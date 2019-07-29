package com.stocker.yahoo.data;

import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CompanyTest {

    @Test
    public void getExistsDay() {
        Company company = new Company();
        company.getDays().add(new Day(LocalDate.now()));
        company.getDays().add(new Day(LocalDate.now().minus(1, ChronoUnit.DAYS)));

        assertEquals("If day is exist in list - it should be returned", new Day(LocalDate.now()), company.getDay(new Day(LocalDate.now())));
    }

    @Test
    public void getNotExistsDay() {
        Company company = new Company();
        company.getDays().add(new Day(LocalDate.now()));
        company.getDays().add(new Day(LocalDate.now().minus(1, ChronoUnit.DAYS)));

        assertEquals("If day is not exist - it should return first day from list", new Day(LocalDate.now().minus(1, ChronoUnit.DAYS)),
                company.getDay(new Day(LocalDate.now().plus(1, ChronoUnit.DAYS))));
    }

    @Test(expected = NoSuchElementException.class)
    public void getEmptyDayList() {
        Company company = new Company();
        company.getDay(new Day(LocalDate.now()));
    }

    @Test
    public void getMaxDayVolumeEmptyList() {
        Company company = new Company();
        assertEquals("max volume should be zero in case of empty history",0, company.getMaxDayVolume());
    }

    @Test
    public void getMaxDayVolume() {
        Company company = new Company();
        Day day = new Day(LocalDate.now());
        day.setVolume(100);
        company.getDays().add(day);
        day = new Day(LocalDate.now().minus(1, ChronoUnit.DAYS));
        day.setVolume(200);
        company.getDays().add(day);

        assertEquals("200 should be return as max volume", 200, company.getMaxDayVolume());
    }

}