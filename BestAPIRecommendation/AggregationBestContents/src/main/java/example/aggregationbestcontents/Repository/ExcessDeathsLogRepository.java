package example.aggregationbestcontents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import example.aggregationbestcontents.Entity.ExcessDeathsLogEntity;

public interface ExcessDeathsLogRepository extends JpaRepository<ExcessDeathsLogEntity, Long> {
}
