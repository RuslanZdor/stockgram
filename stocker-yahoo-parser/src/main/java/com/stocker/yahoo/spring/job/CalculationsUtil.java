package com.stocker.yahoo.spring.job;

import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
class CalculationsUtil {

    private CalculationsUtil() {

    }

    /**
     * Calculate Simple Movement Average for List of Days
     * @param days for calculation
     * @return SMA
     */
    public static double calculateSMA(Set<Double> days) {
        double results = 0;
        for (Double value : days) {
            results += value;
        }
        return days.size() == 0 ? results : results / days.size();
    }

    /**
     * Calculate Exp. Movement Average for List of Days
     * @param value day for calculation
     * @param prevDayValue previous day value
     * @param length of EMA
     * @return SMA
     */
    static double calculateEMA(double value, double prevDayValue, int length) {
        if (prevDayValue < 0) {
            return value;
        }
        double k = 2.0d / (length + 1);
        return (value - prevDayValue) * k + prevDayValue;
    }

}
