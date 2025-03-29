package example.aggregationbestcontents.repository;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import example.aggregationbestcontents.Entity.ExcessDeathEntity;
import example.aggregationbestcontents.Entity.QExcessDeathEntity;
import example.aggregationbestcontents.dto.ExcessDeathRankDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcessDeathRepositoryImpl implements ExcessDeathRepositoryCustom {

	private final JPAQueryFactory queryFactory;
	private final EntityManager em;

	QExcessDeathEntity q = QExcessDeathEntity.excessDeathEntity;

	@Override
	@Transactional
	public void insertNew(String state,
						  LocalDate date,
						  int observed,
						  int threshold) {
		ExcessDeathEntity entity = new ExcessDeathEntity();
		entity.setState(state);
		entity.setWeekEndingDate(date);
		entity.setObservedNumber(observed);
		entity.setUpperBoundThreshold(threshold);

		em.persist(entity);
	}

	@Override
	@Transactional
	public void updateAggregated(String state,
								 LocalDate date,
								 int deltaObserved,
								 int deltaThreshold) {
		queryFactory.update(q)
					.set(q.observedNumber, q.observedNumber.add(deltaObserved))
					.set(q.upperBoundThreshold, q.upperBoundThreshold.add(deltaThreshold))
					.where(q.state.eq(state), q.weekEndingDate.eq(date))
					.execute();
	}

	@Override
	public List<ExcessDeathRankDto> findTop8Ranked() {
		QExcessDeathEntity q = QExcessDeathEntity.excessDeathEntity;

		NumberExpression<Float> combined = q.observedNumber.add(q.upperBoundThreshold.floatValue()).floatValue();

		List<ExcessDeathRankDto> results = queryFactory
			.select(Projections.constructor(
				ExcessDeathRankDto.class,
				q.weekEndingDate,
				q.state,
				q.observedNumber.floatValue(),
				q.upperBoundThreshold,
				combined
			))
			.from(q)
			.orderBy(combined.desc(), q.weekEndingDate.desc())
			.limit(8)
			.fetch();

		for (int i = 0; i < results.size(); i++) {
			results.get(i).setRankNumber(i + 1);
		}

		return results;
	}

}
