package example.aggregationbestcontents.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcessDeathDto {

	private Long id;

	private LocalDate weekEndingDate;
	private String state;
	private Double observedNumber;
	private Integer upperBoundThreshold;

	private Boolean exceedsThreshold;
	private Integer averageExpectedCount;
	private Integer excessEstimate;
	private Integer totalExcessEstimate;
	private Float percentExcessEstimate;

	private Integer year;
	private String type;
	private String outcome;
	private String suppress;
	private String note;
}
