package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

import java.util.SortedSet;

public class CalculateMACDSignal implements ICalculateJob {

    private final static int MACD_COF = 9;

    public void calculate(Company company) {
        company.getDays().stream().filter(day -> day.getMACDSignal() == 0.0d).forEach(day -> {
            SortedSet<Day> prevDays = company.getDays().headSet(day);
            day.setMACDSignal(calculateAverage(day, prevDays.isEmpty() ? 0 : prevDays.last().getMACDSignal()));
        });
    }

    private double calculateAverage(Day day, double prevDayValue) {
        return day.getMACDLine() / CalculateMACDSignal.MACD_COF + prevDayValue * (CalculateMACDSignal.MACD_COF - 1) / CalculateMACDSignal.MACD_COF;
    }
}
