package example.aggregationbestcontents.web;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import example.aggregationbestcontents.DTO.ExcessDeathUpdateRequest;
import example.aggregationbestcontents.Entity.ExcessDeathEntity;
import example.aggregationbestcontents.Repository.ExcessDeathRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExcessDFeathController {

	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final ExcessDeathRepository deathRepository;

	@PostMapping("/excess/increment")
	public ResponseEntity<?> incrementDeathCount(@RequestBody ExcessDeathUpdateRequest request) {
		try {
			String key = String.format("temp:excess:update:%s:%s", request.state(), request.weekEndingDate());

			float plusObserved = 1f;
			int plusThreshold = 1;

			Float updatedObserved = plusObserved;
			Integer updatedThreshold = plusThreshold;

			String existingJson = redisTemplate.opsForValue().get(key);
			if (existingJson != null) {
				JsonNode existing = objectMapper.readTree(existingJson);

				Float currentObserved = existing.has("observedNumber") ? (float)existing.get("observedNumber").asDouble() : 0f;
				Integer currentThreshold = existing.has("upperBoundThreshold") ? existing.get("upperBoundThreshold").asInt() : 0;

				updatedObserved = currentObserved + plusObserved;
				updatedThreshold = currentThreshold + plusThreshold;
			} else {
				// Redis key is missing → new row → also check MySQL
				boolean existsInMySQL = deathRepository.existsByStateAndWeekEndingDate(request.state(), request.weekEndingDate());

				if (!existsInMySQL) {
					ExcessDeathEntity entity = new ExcessDeathEntity();
					entity.setState(request.state());
					entity.setWeekEndingDate(request.weekEndingDate());
					entity.setObservedNumber(0);
					entity.setUpperBoundThreshold(0);
					deathRepository.save(entity);
				}
			}

			Map<String, Object> updated = Map.of("observedNumber", updatedObserved, "upperBoundThreshold", updatedThreshold);

			String json = objectMapper.writeValueAsString(updated);
			redisTemplate.opsForValue().set(key, json);

			return ResponseEntity.ok("✅ Redis saved: " + key);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("❌ Redis save failed: " + e.getMessage());
		}
	}

}
