package org.example.shiftcrmsystem.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Фильтр для периода с учетом типа")
public class PeriodFilterDTO {
    @Schema(
            description = "Тип периода: DAY, MONTH, QUARTER или YEAR",
            example = "DAY",
            allowableValues = {"DAY", "MONTH", "QUARTER", "YEAR"}
    )
    private String periodType;

    @Schema(description = "Дата для фильтрации")
    private LocalDate date;
}
