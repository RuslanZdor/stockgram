package com.stocker.data.job;

import com.stocker.yahoo.data.Day;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculationsUtilTest {

    @Test
    public void calculateSMA() {
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setPrice(i);
            days.add(day);
        }

        CalculationsUtil.calculateSMA(days, 5, Day::getPrice, Day::setSMA5);
        assertEquals(2, days.get(4).getSMA5(), 0.1);
    }


    @Test
    public void calculateEMA() {
        Day day = new Day(LocalDate.now().toEpochDay());
        day.setPrice(100);
        List<Day> days = Collections.singletonList(day);
        CalculationsUtil.calculateEMA(days, 50, Day::setEMA50);
        assertEquals(100, day.getEMA50(), 0.1);
    }
}