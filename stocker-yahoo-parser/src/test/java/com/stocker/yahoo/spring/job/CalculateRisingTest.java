package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;

public class CalculateRisingTest {

    private CalculateRising calculateRising;

    private Company company;

    @Before
    public void init() {
        calculateRising = new CalculateRising();
    }

    @Test
    public void calculateRising() {
        company = new Company();
        for (int i = 1; i <= 2; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }

        calculateRising.calculate(company);
        assertTrue(company.getDays().last().isRising());
    }
}