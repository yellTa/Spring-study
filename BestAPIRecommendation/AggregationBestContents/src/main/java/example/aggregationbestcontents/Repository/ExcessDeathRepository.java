package example.aggregationbestcontents.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import example.aggregationbestcontents.Entity.ExcessDeathEntity;

public interface ExcessDeathRepository  extends JpaRepository<ExcessDeathEntity, Long> , ExcessDeathRepositoryCustom {
	boolean existsByStateAndWeekEndingDate(String state, LocalDate weekEndingDate);
	Optional<ExcessDeathEntity> findByStateAndWeekEndingDate(String state, LocalDate weekEndingDate);
}
