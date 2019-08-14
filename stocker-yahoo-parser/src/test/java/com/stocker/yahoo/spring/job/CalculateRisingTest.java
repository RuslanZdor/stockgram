package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateRisingTest {

    @Autowired
    private CalculateRising ema;

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
        assertTrue(company.getDays().last().isRising());
    }
}