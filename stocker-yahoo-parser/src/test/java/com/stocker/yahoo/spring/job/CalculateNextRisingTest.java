package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.spring.StockYahooConfigurationForTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockYahooConfigurationForTest.class})
public class CalculateNextRisingTest {

    @Autowired
    private CalculateNextRising ema;

    private Company company;

    @Test
    public void calculateRising() {
        company = new Company();
        for (int i = 1; i <= 2; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }

        ema.calculate(company);
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

        ema.calculate(company);
        assertFalse(company.getDays().first().isNextRise());
        assertFalse(company.getDays().last().isNextRise());
    }
}