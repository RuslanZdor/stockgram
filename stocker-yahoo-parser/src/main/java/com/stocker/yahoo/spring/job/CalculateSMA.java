package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Calculate simple moving average for company/market/industry
 */
@Component
public class CalculateSMA implements ICalculateJob {
    public void calculate(Company company) {
        for (Day day : company.getDays()) {
            NavigableSet<Day> sortedSet = company.getDays().headSet(day, true);
            day.setFiveSMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(5).map(Day::getPrice).collect(Collectors.toSet())));
            day.setTenSMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(10).map(Day::getPrice).collect(Collectors.toSet())));
            day.setFifteenSMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(15).map(Day::getPrice).collect(Collectors.toSet())));
            day.setTwentySMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(20).map(Day::getPrice).collect(Collectors.toSet())));
            day.setTwentyFiveSMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(25).map(Day::getPrice).collect(Collectors.toSet())));
            day.setThirtySMA(CalculationsUtil.calculateSMA(sortedSet.stream().sorted(Collections.reverseOrder()).limit(30).map(Day::getPrice).collect(Collectors.toSet())));
        }
    }
}
