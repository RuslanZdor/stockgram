package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

public class CalculateMACDLine implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getMACDLine() == 0.0d).forEach((Day day) -> day.setMACDLine(day.getEMA10() / day.getEMA50()));
    }
}
