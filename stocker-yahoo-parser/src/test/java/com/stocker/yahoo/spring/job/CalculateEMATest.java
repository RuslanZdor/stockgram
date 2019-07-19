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
    private CalculateEMA ema;

    private Company company;

    @Before
    public void prepareObject() {
        company = new Company();
        for (int i = 1; i <= 31; i++) {
            Day day = new Day(LocalDate.now().minus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }
    }

    @Test
    public void calculate() {
        ema.calculate(company);
        assertEquals(4.9, company.getDays().last().getFiveEMA(), 0.1);
        assertEquals(9.6, company.getDays().last().getTenEMA(), 0.1);
        assertEquals(13.2, company.getDays().last().getFifteenEMA(), 0.1);
        assertEquals(15.9, company.getDays().last().getTwentyEMA(), 0.1);
        assertEquals(17.9, company.getDays().last().getTwentyFiveEMA(), 0.1);
        assertEquals(19.5, company.getDays().last().getThirtyEMA(), 0.1);
    }

}