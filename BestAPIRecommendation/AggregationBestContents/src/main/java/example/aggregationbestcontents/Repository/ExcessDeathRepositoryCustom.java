package example.aggregationbestcontents.repository;

import java.time.LocalDate;
import java.util.List;

import example.aggregationbestcontents.dto.ExcessDeathRankDto;

public interface ExcessDeathRepositoryCustom {

	void insertNew(String state,
				   LocalDate date,
				   int observed,
				   int threshold);

	void updateAggregated(String state,
						  LocalDate date,
						  int deltaObserved,
						  int deltaThreshold);

	List<ExcessDeathRankDto> findTop8Ranked();

}
