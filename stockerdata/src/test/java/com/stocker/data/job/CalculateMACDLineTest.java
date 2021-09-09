package com.stocker.data.job;

import com.stocker.data.job.stock.CalculateEMA;
import com.stocker.data.job.stock.CalculateMACDLine;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateMACDLineTest {

    private CalculateMACDLine calculateMACDLine = new CalculateMACDLine();
    private CalculateEMA calculateEMA = new CalculateEMA();

    @Test
    public void calculate() {
        Company company = new Company();
        company.getDays().add(Day.builder()
                .price(10)
                .build());
        company.getDays().add(Day.builder()
                .price(20)
                .build());
        calculateEMA.calculate(company);
        calculateMACDLine.calculate(company);

        assertEquals(0.86, company.getDays().get(1).getMACDLine(), 0.1);
    }
}