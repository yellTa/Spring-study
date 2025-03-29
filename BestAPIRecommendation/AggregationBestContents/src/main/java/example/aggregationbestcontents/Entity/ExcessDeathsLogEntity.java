package example.aggregationbestcontents.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excess_deaths_log")
@Getter
@Setter
public class ExcessDeathsLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate weekEndingDate;
	private String state;
	private Float observedNumber;
	private Integer upperBoundThreshold;
	private Float combinedValue;
	private LocalDateTime createdAt;
	private Integer rankNumber;
}
