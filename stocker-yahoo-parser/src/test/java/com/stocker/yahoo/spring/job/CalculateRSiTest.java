package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateRSiTest {

    @Autowired
    private CalculateRSI ema;

    private Company company;

    @Test
    public void calculateIncreasePrice() {
        company = new Company();
        for (int i = 1; i <= 31; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }
        Day day = new Day(LocalDate.now().plus(32, ChronoUnit.DAYS));
        day.setPrice(30);
        company.getDays().add(day);

        ema.calculate(company);
        assertTrue(company.getDays().last().getThirtyRSI() > 0.5);
    }

    @Test
    public void calculateDecreasePrice() {
        company = new Company();
        for (int i = 1; i <= 31; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(50 - i);
            company.getDays().add(day);
        }

        ema.calculate(company);
        assertTrue(company.getDays().last().getThirtyRSI() < 0.5);
    }
}