package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import org.springframework.stereotype.Component;

import java.util.SortedSet;

@Component
public class CalculateMACDSignal implements ICalculateJob {

    private final static int MACD_COF = 9;

    public void calculate(Company company) {
        company.getDays().forEach(day -> {
            SortedSet<Day> prevDays = company.getDays().headSet(day);
            day.setMACDSignal(calculateAverage(day, prevDays.isEmpty() ? 0 : prevDays.last().getMACDSignal()));
        });
    }

    private double calculateAverage(Day day, double prevDayValue) {
        return day.getMACDLine() * 1.0d / CalculateMACDSignal.MACD_COF + prevDayValue * (CalculateMACDSignal.MACD_COF - 1) / CalculateMACDSignal.MACD_COF;
    }
}
