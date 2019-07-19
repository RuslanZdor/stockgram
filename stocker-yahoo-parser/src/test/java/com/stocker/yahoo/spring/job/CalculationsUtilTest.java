package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Day;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CalculationsUtilTest {

    @Test
    public void calculateSMA() {
        SortedSet<Day> days = new TreeSet();
        for (int i = 0; i < 5; i++) {
            Day day = new Day(LocalDate.now().plus(i, ChronoUnit.DAYS));
            day.setPrice(i);
            days.add(day);
        }

        assertEquals(2, CalculationsUtil.calculateSMA(days.stream().map(day -> day.getPrice()).collect(Collectors.toSet())), 0.1);
    }


    @Test
    public void calculateEMA() {
        Day day = new Day(LocalDate.now());
        day.setPrice(100);
        assertEquals(55, CalculationsUtil.calculateEMA(day, 50, 10), 0.1);
    }
}