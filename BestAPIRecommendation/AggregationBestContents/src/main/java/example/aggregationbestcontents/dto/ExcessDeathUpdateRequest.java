package example.aggregationbestcontents.dto;

import java.time.LocalDate;

public record ExcessDeathUpdateRequest(
	String state,
	LocalDate weekEndingDate
) {}
