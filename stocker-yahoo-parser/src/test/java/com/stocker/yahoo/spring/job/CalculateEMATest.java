package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class CalculateEMATest {

    private CalculateSMA sma;

    private CalculateEMA ema;

    private Company company;

    @Before
    public void init() {
        sma = new CalculateSMA();
        ema = new CalculateEMA();
    }

    @Before
    public void prepareObject() {
        company = new Company();
        for (int i = 1; i <= 201; i++) {
            Day day = new Day(LocalDate.now().minus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }
    }

    @Test
    public void calculate() {
        sma.calculate(company);
        ema.calculate(company);
        assertEquals(3.0, company.getDays().last().getEMA5(), 0.1);
        assertEquals(5.5, company.getDays().last().getEMA10(), 0.1);
        assertEquals(10.5, company.getDays().last().getEMA20(), 0.1);
        assertEquals(25.5, company.getDays().last().getEMA50(), 0.1);
        assertEquals(87, company.getDays().last().getEMA200(), 0.1);
    }

}