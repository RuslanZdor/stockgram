package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

import java.util.Collections;
import java.util.NavigableSet;
import java.util.stream.Collectors;

/**
 * Calculate simple moving average for company/market/industry
 */
public class CalculateSMA implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getSMA50() == 0.0).forEach(day -> {
            NavigableSet<Day> sortedSet = company.getDays().headSet(day, true);
            day.setSMA5(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(5).map(Day::getPrice).collect(Collectors.toSet())));
            day.setSMA10(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(10).map(Day::getPrice).collect(Collectors.toSet())));
            day.setSMA20(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(20).map(Day::getPrice).collect(Collectors.toSet())));
            day.setSMA50(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(50).map(Day::getPrice).collect(Collectors.toSet())));
            day.setSMA200(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(200).map(Day::getPrice).collect(Collectors.toSet())));
        });
    }
}
