package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

public class CalculateMACDSignal implements ICalculateJob {

    private final static int MACD_COF = 9;

    public void calculate(Company company) {
        double prevValue = 0.0;
        for (Day day : company.getDays()) {
            day.setMACDSignal(calculateAverage(day, prevValue));
            prevValue =day.getMACDSignal();
        }
    }

    private double calculateAverage(Day day, double prevDayValue) {
        return day.getMACDLine() / CalculateMACDSignal.MACD_COF
                + prevDayValue * (CalculateMACDSignal.MACD_COF - 1) / CalculateMACDSignal.MACD_COF;
    }
}
