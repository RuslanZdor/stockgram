package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class CalculateSMATest {

    private CalculateSMA calculateSMA;

    @Before
    public void init() {
        calculateSMA = new CalculateSMA();
    }

    @Test
    public void calculate() {
        long time = System.currentTimeMillis();
        Company company = new Company();
        for (int i = 1; i <= 201; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(i);
            company.getDays().add(day);
        }

        calculateSMA.calculate(company);

        assertEquals(199, company.getDays().get(200).getSMA5(), 0.1);
        assertEquals(196.5, company.getDays().get(200).getSMA10(), 0.1);
        assertEquals(191.5, company.getDays().get(200).getSMA20(), 0.1);
        assertEquals(176.5, company.getDays().get(200).getSMA50(), 0.1);
        assertEquals(101.5, company.getDays().get(200).getSMA200(), 0.1);
    }

    @Test
    public void firstDay() {
        Company company = new Company();
        Day day = new Day(LocalDate.now().toEpochDay());
        day.setPrice(10);
        company.getDays().add(day);

        calculateSMA.calculate(company);

        assertEquals(10, company.getDays().get(0).getSMA5(), 0.1);
        assertEquals(10, company.getDays().get(0).getSMA10(), 0.1);
        assertEquals(10, company.getDays().get(0).getSMA20(), 0.1);
        assertEquals(10, company.getDays().get(0).getSMA50(), 0.1);
        assertEquals(10, company.getDays().get(0).getSMA200(), 0.1);
    }
}