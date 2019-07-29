package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

@Component
public class CalculateMACDLine implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().forEach((Day day) -> day.setMACDLine(day.getEMA10() / day.getEMA50()));
    }
}
