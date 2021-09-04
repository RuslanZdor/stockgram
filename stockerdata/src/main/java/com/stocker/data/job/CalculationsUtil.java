package com.stocker.data.job;

import com.stocker.yahoo.data.Day;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Singleton
class CalculationsUtil {

    private CalculationsUtil() {

    }

    /**
     * Calculate Simple Movement Average for List of Days
     * @param days for calculation
     */
    public static void calculateSMA(List<Day> days, int size, GetDoubleValue getter, UpdateDay update) {
        if (size > 0) {
            Queue<Double> currentQueue = new LinkedList<>();
            double results = 0;
            for (Day day : days) {
                double value = getter.get(day);
                results += value;
                currentQueue.add(value);
                if (currentQueue.size() > size) {
                    results -= currentQueue.poll();
                }
                if (!day.isFinished()) {
                    update.set(day, results / Math.min(size, currentQueue.size()));
                }
            }
        }
    }

    public interface UpdateDay {
        void set(Day day, double value);
    }

    public interface GetDoubleValue {
        double get(Day day);
    }

    /**
     * Calculate Exp. Movement Average for List of Days
     * @param days for calculation
     */
    static void calculateEMA(List<Day> days, int size, UpdateDay update) {
        if (size > 0) {
            double prevDayValue = 0.0;
            double results;
            for (Day day : days) {
                results = day.getPrice();
                if (prevDayValue == 0) {
                    if (!day.isFinished()) {
                        update.set(day, day.getPrice());
                    }
                } else {
                    double k = 2.0d / (size + 1);
                    results = (results - prevDayValue) * k + prevDayValue;
                    if (!day.isFinished()) {
                        update.set(day, results);
                    }
                }
                prevDayValue = results;
            }
        }

    }

}
