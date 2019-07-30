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

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateEMATest {

    @Autowired
    private CalculateSMA sma;

    @Autowired
    private CalculateEMA ema;

    private Company company;

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
        assertEquals(7.8, company.getDays().last().getEMA20(), 0.1);
        assertEquals(10.0, company.getDays().last().getEMA50(), 0.1);
        assertEquals(11.9, company.getDays().last().getEMA200(), 0.1);
    }

}