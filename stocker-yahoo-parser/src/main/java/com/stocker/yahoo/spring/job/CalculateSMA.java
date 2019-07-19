package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Calculate simple moving average for company/market/industry
 */
@Component
public class CalculateSMA implements ICalculateJob {
    public void calculate(Company company) {
        for (Day day : company.getDays()) {

            Day nextDay = new Day(day.getDate().plus(1, ChronoUnit.DAYS));
            Day searchDay = new Day(day.getDate());
            searchDay.setDate(searchDay.getDate().minus(5, ChronoUnit.DAYS));
            day.setFiveSMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
            searchDay.setDate(day.getDate().minus(10, ChronoUnit.DAYS));
            day.setTenSMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
            searchDay.setDate(day.getDate().minus(15, ChronoUnit.DAYS));
            day.setFifteenSMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
            searchDay.setDate(day.getDate().minus(20, ChronoUnit.DAYS));
            day.setTwentySMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
            searchDay.setDate(day.getDate().minus(25, ChronoUnit.DAYS));
            day.setTwentyFiveSMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
            searchDay.setDate(day.getDate().minus(30, ChronoUnit.DAYS));
            day.setThirtySMA(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(Day::getPrice).collect(Collectors.toSet())
            ));
        }
    }
}
