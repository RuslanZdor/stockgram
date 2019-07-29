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
            day.setFiveEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getFiveEMA(), 5));
            day.setTenEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getTenEMA(), 10));
            day.setFifteenEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getFifteenEMA(), 15));
            day.setTwentyEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getTwentyEMA(), 20));
            day.setTwentyFiveEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getTwentyFiveEMA(), 25));
            day.setThirtyEMA(CalculationsUtil.calculateEMA(day.getPrice(), prevDays.isEmpty() ? day.getPrice() : prevDays.last().getThirtyEMA(), 30));
        });
    }
}
