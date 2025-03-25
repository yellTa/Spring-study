package example.aggregationbestcontents.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "excess_deaths")
@Getter
@Setter
public class ExcessDeathEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	private LocalDate weekEndingDate;
	private String state;
	private Integer observedNumber;
	private Integer upperBoundThreshold;

}

