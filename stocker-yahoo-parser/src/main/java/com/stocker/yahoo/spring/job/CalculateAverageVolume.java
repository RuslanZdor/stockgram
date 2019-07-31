package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Calculate SMA volume
 */
@Component
public class CalculateAverageVolume implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getAverageVolume200() == 0).forEach(day -> {
            Day searchDay = new Day(day.getDate());

            Day nextDay = new Day(day.getDate().plus(1, ChronoUnit.DAYS));
            searchDay.setDate(day.getDate().minus(5, ChronoUnit.DAYS));
            day.setAverageVolume5(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(day.getDate().minus(10, ChronoUnit.DAYS));
            day.setAverageVolume10(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(day.getDate().minus(20, ChronoUnit.DAYS));
            day.setAverageVolume20(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(day.getDate().minus(50, ChronoUnit.DAYS));
            day.setAverageVolume50(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(day.getDate().minus(200, ChronoUnit.DAYS));
            day.setAverageVolume200(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));
        });
    }
}
