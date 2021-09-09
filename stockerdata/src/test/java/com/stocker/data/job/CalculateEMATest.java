package com.stocker.data.job;

import com.stocker.data.job.stock.CalculateEMA;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;

public class CalculateEMATest {

    private CalculateEMA ema;

    private Company company;

    @Before
    public void init() {
        ema = new CalculateEMA();
    }

    @Before
    public void prepareObject() {
        company = new Company();
        for (int i = 200; i > 0; i--) {
            Day day = new Day(LocalDate.now().minus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(i);
            company.getDays().add(day);
        }
    }

    @Test
    public void calculate() {
        ema.calculate(company);
        assertEquals(3.0, company.getDays().get(199).getEMA5(), 0.1);
        assertEquals(5.5, company.getDays().get(199).getEMA10(), 0.1);
        assertEquals(10.5, company.getDays().get(199).getEMA20(), 0.1);
        assertEquals(25.5, company.getDays().get(199).getEMA50(), 0.1);
        assertEquals(87, company.getDays().get(199).getEMA200(), 0.2);
    }

}