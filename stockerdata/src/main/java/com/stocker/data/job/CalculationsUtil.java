package com.stocker.data.job;

import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.MarketDay;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Singleton
public class CalculationsUtil {

    private CalculationsUtil() {

    }

    /**
     * Calculate Simple Movement Average for List of Days
     * @param days for calculation
     */
    public static void calculateSMA(List<Day> days, int size, GetDoubleValue<Day> getter, UpdateDay<Day> update) {
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
                update.set(day, results / Math.min(size, currentQueue.size()));
            }
        }
    }

    public static void calculateMarketSMA(List<MarketDay> days,
                                          int size,
                                          GetDoubleValue<MarketDay> getter,
                                          UpdateDay<MarketDay> update) {
        if (size > 0) {
            Queue<Double> currentQueue = new LinkedList<>();
            double results = 0;
            for (MarketDay day : days) {
                double value = getter.get(day);
                results += value;
                currentQueue.add(value);
                if (currentQueue.size() > size) {
                    results -= currentQueue.poll();
                }
                update.set(day, results / Math.min(size, currentQueue.size()));
            }
        }
    }

    /**
     * Calculate Simple Movement Average for List of Days
     * @param days for calculation
     */
    public static void calculateRSIMovement(List<Day> days,
                                            int size,
                                            GetRSIValue<Day> calculation,
                                            UpdateDay<Day> update) {
        if (size > 0) {
            Queue<Double> currentQueue = new LinkedList<>();
            double results = 0.0;
            double prevDayPrice = 0.0;
            for (Day day : days) {
                double value = day.getPrice();
                if (prevDayPrice != 0.0) {
                    double dailyValue = calculation.get(day, prevDayPrice);
                    results += dailyValue;
                    currentQueue.add(dailyValue);
                    if (currentQueue.size() > size) {
                        results -= currentQueue.poll();
                    }
                    if (!day.isFinished()) {
                        update.set(day, results / Math.min(size, currentQueue.size()));
                    }
                }
                prevDayPrice = value;
            }
        }
    }

    public interface UpdateDay<T> {
        void set(T day, double value);
    }

    public interface GetDoubleValue<T> {
        double get(T day);
    }

    public interface GetRSIValue<T> {
        double get(T day, double prevValue);
    }

    /**
     * Calculate Exp. Movement Average for List of Days
     * @param days for calculation
     */
    public static void calculateEMA(List<Day> days, int size, UpdateDay<Day> update) {
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
