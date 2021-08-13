package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Calculate SMA volume
 */
public class CalculateAverageVolume implements ICalculateJob {
    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getAverageVolume200() == 0).forEach(day -> {
            Day searchDay = new Day(day.getDate());

            Day nextDay = new Day(new Date(2020, Calendar.NOVEMBER, 21).getTime());
            searchDay.setDate(new Date(2020, Calendar.NOVEMBER, 15 ).getTime());
            day.setAverageVolume5(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(new Date(2020, Calendar.NOVEMBER, 10).getTime());
            day.setAverageVolume10(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

            searchDay.setDate(new Date(2020, Calendar.NOVEMBER, 1).getTime());
            day.setAverageVolume20(CalculationsUtil.calculateSMA(
                    company.getDays().subSet(searchDay, nextDay)
                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
            ));

//            searchDay.setDate(day.getDate().minus(50, ChronoUnit.DAYS));
//            day.setAverageVolume50(CalculationsUtil.calculateSMA(
//                    company.getDays().subSet(searchDay, nextDay)
//                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
//            ));
//
//            searchDay.setDate(day.getDate().minus(200, ChronoUnit.DAYS));
//            day.setAverageVolume200(CalculationsUtil.calculateSMA(
//                    company.getDays().subSet(searchDay, nextDay)
//                            .stream().map(day1 -> (double) day1.getVolume()).collect(Collectors.toSet())
//            ));
        });
    }
}
