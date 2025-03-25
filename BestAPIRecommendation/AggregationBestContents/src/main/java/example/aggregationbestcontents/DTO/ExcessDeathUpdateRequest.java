package example.aggregationbestcontents.DTO;

import java.time.LocalDate;

public record ExcessDeathUpdateRequest(
	String state,
	LocalDate weekEndingDate
) {}
