package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CalculateRSITest {

    private CalculateRSI rsi;

    private Company company;

    @Before
    public void init() {
        rsi = new CalculateRSI();
    }

    @Test
    public void calculateIncreasePrice() {
        company = new Company();
        for (int i = 1; i <= 31; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(i);
            company.getDays().add(day);
        }
        Day day = new Day(LocalDate.now().plus(32, ChronoUnit.DAYS).toEpochDay());
        day.setPrice(30);
        company.getDays().add(day);

        rsi.calculate(company);
//        assertTrue(company.getDays().last().getRSI200() > 0.5);
    }

    @Test
    public void calculateDecreasePrice() {
        company = new Company();
        for (int i = 1; i <= 31; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(50 - i);
            company.getDays().add(day);
        }

        rsi.calculate(company);
//        assertTrue(company.getDays().last().getRSI200() < 0.5);
    }
}