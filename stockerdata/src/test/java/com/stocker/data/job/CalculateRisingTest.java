package com.stocker.data.job;

import com.stocker.data.job.stock.CalculateRising;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;

public class CalculateRisingTest {

    private CalculateRising calculateRising;

    @Before
    public void init() {
        calculateRising = new CalculateRising();
    }

    @Test
    public void calculateRising() {
        Company company = new Company();
        for (int i = 1; i <= 2; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(i);
            company.getDays().add(day);
        }

        calculateRising.calculate(company);
        assertTrue(company.getDays().get(1).isRising());
    }
}