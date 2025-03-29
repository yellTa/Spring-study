package example.aggregationbestcontents.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.aggregationbestcontents.Entity.ExcessDeathsLogEntity;
import example.aggregationbestcontents.dto.ExcessDeathRankDto;
import example.aggregationbestcontents.repository.ExcessDeathRepository;
import example.aggregationbestcontents.repository.ExcessDeathsLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchJobService {

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final ExcessDeathRepository excessDeathRepository;
	private final ExcessDeathsLogRepository logRepository;

	@Scheduled(cron = "*/15 * * * * *")
	public void doBatchJob() {
		loadRedisData();
		saveRankedToLog();
	}

	public void loadRedisData() {
		Set<String> newKeys = redisTemplate.keys("temp:excess:new:*");
		Set<String> updateKeys = redisTemplate.keys("temp:excess:update:*");

		processKeys(newKeys, true);
		processKeys(updateKeys, false);
	}

	private void processKeys(Set<String> keys,
							 boolean isNew) {
		if (keys == null || keys.isEmpty())
			return;

		for (String key : keys) {
			try {
				String json = redisTemplate.opsForValue().get(key);
				if (json == null)
					continue;

				JsonNode node = objectMapper.readTree(json);
				String[] parts = key.split(":");
				String state = parts[3];
				LocalDate date = LocalDate.parse(parts[4]);

				int observed = node.get("observedNumber").asInt();
				int threshold = node.get("upperBoundThreshold").asInt();

				if (isNew) {
					excessDeathRepository.insertNew(state, date, observed, threshold);
					log.info("🆕 NEW => " + key + " → " + observed + "/" + threshold);
				} else {
					excessDeathRepository.updateAggregated(state, date, observed, threshold);
					log.info("🔁 UPDATE => " + key + " → " + observed + "/" + threshold);
				}
				redisTemplate.delete(key);

			} catch (Exception e) {
				log.info("❌ Failed to parse Redis key: " + key);
				log.info("💔= {}",e.toString());
			}
		}
	}

	public void saveRankedToLog() {
		List<ExcessDeathRankDto> top8 = excessDeathRepository.findTop8Ranked();

		List<ExcessDeathsLogEntity> logs = top8.stream().map(dto -> {
			ExcessDeathsLogEntity entity = new ExcessDeathsLogEntity();
			entity.setWeekEndingDate(dto.getWeekEndingDate());
			entity.setState(dto.getState());
			entity.setObservedNumber(Float.valueOf(dto.getObservedNumber()));
			entity.setUpperBoundThreshold(dto.getUpperBoundThreshold());
			entity.setCombinedValue(dto.getCombinedValue());
			entity.setRankNumber(dto.getRankNumber());
			entity.setCreatedAt(LocalDateTime.now());
			return entity;
		}).toList();

		logRepository.saveAll(logs);
		log.info("✅ Top 8 log inserted to DB");

		for (ExcessDeathRankDto dto : top8) {
			try {
				String key = String.format("temp:excess:update:%s:%s", dto.getState(), dto.getWeekEndingDate());
				Map<String, Object> value = Map.of(
					"observedNumber", dto.getObservedNumber(),
					"upperBoundThreshold", dto.getUpperBoundThreshold()
				);
				String json = objectMapper.writeValueAsString(value);
				redisTemplate.opsForValue().set(key, json);
				log.info("📦 Redis set: " + key);
			} catch (Exception e) {
				log.error("❌ Failed to cache individual key", e);
			}
		}
		log.info("Aggregation data saved to redis ");
	}

}
