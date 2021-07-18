package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        for (int i = 1; i <= 2; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }

        calculateNextRising.calculate(company);
        assertTrue(company.getDays().first().isNextRise());
        assertFalse(company.getDays().last().isNextRise());
    }

    @Test
    public void calculateFalseRising() {
        company = new Company();
        for (int i = 1; i <= 2; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(3 - i);
            company.getDays().add(day);
        }

        calculateNextRising.calculate(company);
        assertFalse(company.getDays().first().isNextRise());
        assertFalse(company.getDays().last().isNextRise());
    }
}