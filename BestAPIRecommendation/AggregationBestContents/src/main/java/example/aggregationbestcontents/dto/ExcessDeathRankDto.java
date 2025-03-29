package example.aggregationbestcontents.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcessDeathRankDto {
	private LocalDate weekEndingDate;
	private String state;
	private Float observedNumber;
	private Integer upperBoundThreshold;
	private Float combinedValue;
	private Integer rankNumber;

	public ExcessDeathRankDto(LocalDate weekEndingDate,
							  String state,
							  Float observedNumber,
							  Integer upperBoundThreshold,
							  Float combinedValue) {
		this.weekEndingDate = weekEndingDate;
		this.state = state;
		this.observedNumber = observedNumber;
		this.upperBoundThreshold = upperBoundThreshold;
		this.combinedValue = combinedValue;
	}
}
