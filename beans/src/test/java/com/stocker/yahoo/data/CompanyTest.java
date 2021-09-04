package com.stocker.yahoo.data;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CompanyTest {

    @Test
    public void getExistsDay() {
        Company company = new Company();
        company.getDays().add(new Day(new Date(2020, Calendar.NOVEMBER, 19).getTime()));
        company.getDays().add(new Day(new Date(2020, Calendar.NOVEMBER, 20).getTime()));

        assertEquals("If day is exist in list - it should be returned",
                new Day(new Date(2020, Calendar.NOVEMBER, 20).getTime()),
                company.getDay(new Day(new Date(2020, Calendar.NOVEMBER, 20).getTime())));
    }

    @Test
    public void getNotExistsDay() {
        Company company = new Company();
        company.getDays().add(new Day(new Date(2020, Calendar.NOVEMBER, 19).getTime()));
        company.getDays().add(new Day(new Date(2020, Calendar.NOVEMBER, 20).getTime()));

        assertEquals("If day is not exist - it should return first day from list",
                new Day(new Date(2020, Calendar.NOVEMBER, 19).getTime()),
                company.getDay(new Day(new Date(2020, Calendar.NOVEMBER, 21).getTime())));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getEmptyDayList() {
        Company company = new Company();
        company.getDay(new Day(LocalDate.now().toEpochDay()));
    }

    @Test
    public void getMaxDayVolumeEmptyList() {
        Company company = new Company();
        assertEquals("max volume should be zero in case of empty history",0, company.getMaxDayVolume());
    }

    @Test
    public void getMaxDayVolume() {
        Company company = new Company();
        Day day = new Day(new Date(2020, Calendar.NOVEMBER, 20).getTime());
        day.setVolume(100);
        company.getDays().add(day);
        day = new Day(new Date(2020, Calendar.NOVEMBER, 21).getTime());
        day.setVolume(200);
        company.getDays().add(day);

        assertEquals("200 should be return as max volume", 200, company.getMaxDayVolume());
    }

}