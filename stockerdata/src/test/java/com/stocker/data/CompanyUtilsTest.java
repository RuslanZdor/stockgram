package com.stocker.data;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class CompanyUtilsTest {

    @Test
    public void needUpdateNull() {
        Company company = new Company();
        assertTrue(CompanyUtils.needUpdate(company));

        company.getDays().add(new Day());
        assertTrue(CompanyUtils.needUpdate(company));
    }

    @Test
    public void needUpdate() {
        Company company = new Company();
        Day day = new Day();
        day.setLastUpdate(LocalDateTime.now().minusMinutes(10));
        company.getDays().add(day);
        assertTrue(CompanyUtils.needUpdate(company));

        day.setLastUpdate(LocalDateTime.now().minusMinutes(1));
        assertFalse(CompanyUtils.needUpdate(company));
    }
}