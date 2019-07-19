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

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateSMATest {

    @Autowired
    private CalculateSMA calculateSMA;

    @Test
    public void calculate() {
        Company company = new Company();
        for (int i = 1; i <= 30; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            company.getDays().add(day);
        }

        calculateSMA.calculate(company);

        assertEquals(27.5, company.getDays().last().getFiveSMA(), 0.1);
        assertEquals(25, company.getDays().last().getTenSMA(), 0.1);
        assertEquals(22.5, company.getDays().last().getFifteenSMA(), 0.1);
        assertEquals(20, company.getDays().last().getTwentySMA(), 0.1);
        assertEquals(17.5, company.getDays().last().getTwentyFiveSMA(), 0.1);
        assertEquals(15.5, company.getDays().last().getThirtySMA(), 0.1);
    }

    @Test
    public void firstDay() {
        Company company = new Company();
        Day day = new Day(LocalDate.now());
        day.setPrice(10);
        company.getDays().add(day);

        calculateSMA.calculate(company);

        assertEquals(10, company.getDays().last().getFiveSMA(), 0.1);
        assertEquals(10, company.getDays().last().getTenSMA(), 0.1);
        assertEquals(10, company.getDays().last().getFifteenSMA(), 0.1);
        assertEquals(10, company.getDays().last().getTwentySMA(), 0.1);
        assertEquals(10, company.getDays().last().getTwentyFiveSMA(), 0.1);
        assertEquals(10, company.getDays().last().getThirtySMA(), 0.1);
    }
}