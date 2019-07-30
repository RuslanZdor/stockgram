package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.util.SortedSet;

/**
 * Calculate Exponential moving average for company/market/industry
 */
@Component
public class CalculateEMA implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getThirtyEMA() == 0.0d).forEach(day -> {
            SortedSet<Day> prevDays = company.getDays().headSet(day);
            day.setEMA5(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getEMA5(), 5));
            day.setEMA10(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getEMA10(), 10));
            day.setEMA20(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getEMA20(), 20));
            day.setEMA50(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getEMA50(), 50));
            day.setEMA200(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getEMA200(), 200));
        });
    }
}
