package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import org.junit.Before;
import org.junit.Test;

public class CalculateNextRisingTest {

    private CalculateNextRising calculateNextRising;

    private Company company;

    @Before
    public void init() {
        calculateNextRising = new CalculateNextRising();
    }

    @Test
    public void calculateRising() {
        company = new Company();
//        for (int i = 1; i <= 2; i++) {
//            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
//            day.setPrice(i);
//            company.getDays().add(day);
//        }
//
//        calculateNextRising.calculate(company);
//        assertTrue(company.getDays().first().isNextRise());
//        assertFalse(company.getDays().last().isNextRise());
    }

    @Test
    public void calculateFalseRising() {
        company = new Company();
//        for (int i = 1; i <= 2; i++) {
//            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
//            day.setPrice(3 - i);
//            company.getDays().add(day);
//        }
//
//        calculateNextRising.calculate(company);
//        assertFalse(company.getDays().first().isNextRise());
//        assertFalse(company.getDays().last().isNextRise());
    }
}