package com.stocker.data.job.stock;

import com.stocker.data.job.ICalculateJob;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

public class CalculateMACDLine implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays()
                .forEach((Day day) -> day.setMACDLine(day.getEMA10() - day.getEMA20()));
    }
}
