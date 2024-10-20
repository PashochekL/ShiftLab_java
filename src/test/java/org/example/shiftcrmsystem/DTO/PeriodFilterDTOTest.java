package org.example.shiftcrmsystem.DTO;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PeriodFilterDTOTest {

    @Test
    void testConstructorWithParameters() {
        String periodType = "DAY";
        LocalDate date = LocalDate.of(2024, 10, 20);

        PeriodFilterDTO periodFilterDTO = new PeriodFilterDTO();
        periodFilterDTO.setPeriodType(periodType);
        periodFilterDTO.setDate(date);

        assertEquals(periodType, periodFilterDTO.getPeriodType());
        assertEquals(date, periodFilterDTO.getDate());
    }

    @Test
    void testDefaultConstructor() {
        PeriodFilterDTO periodFilterDTO = new PeriodFilterDTO();

        assertNull(periodFilterDTO.getPeriodType());
        assertNull(periodFilterDTO.getDate());
    }

    @Test
    void testSettersAndGetters() {
        PeriodFilterDTO periodFilterDTO = new PeriodFilterDTO();

        String periodType = "MONTH";
        LocalDate date = LocalDate.of(2024, 10, 20);

        periodFilterDTO.setPeriodType(periodType);
        periodFilterDTO.setDate(date);

        assertEquals(periodType, periodFilterDTO.getPeriodType());
        assertEquals(date, periodFilterDTO.getDate());
    }
}
