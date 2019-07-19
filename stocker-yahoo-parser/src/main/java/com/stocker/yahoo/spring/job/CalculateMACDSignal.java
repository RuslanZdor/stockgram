package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.util.SortedSet;

@Component
public class CalculateMACDSignal implements ICalculateJob {

    private static int MACD_COF = 9;

    public void calculate(Company company) {
        company.getDays().forEach(day -> {
            SortedSet<Day> prevDays = company.getDays().headSet(day);
            day.setMACDSignal(calculateAverage(day, prevDays.isEmpty() ? 0 : prevDays.last().getMACDSignal(), MACD_COF));
        });
    }

    private double calculateAverage(Day day, double prevDayValue, int length) {
        return day.getMACDLine() * 1.0d / length + prevDayValue * (length - 1) / length;
    }
}
