package com.stocker.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * daily information
 */

@Data
@NoArgsConstructor
public class Day implements Comparable<Day>{

    private int id;

    private LocalDate date;
    private long volume;
    private double price;
    private double minPrice;
    private double maxPrice;
    private double fiveEMA;
    private double tenEMA;
    private double fifteenEMA;
    private double twentyEMA;
    private double twentyFiveEMA;
    private double thirtyEMA;

    private double fiveSMA;
    private double tenSMA;
    private double fifteenSMA;
    private double twentySMA;
    private double twentyFiveSMA;
    private double thirtySMA;

    private double fiveRSI;
    private double tenRSI;
    private double fifteenRSI;
    private double twentyRSI;
    private double twentyFiveRSI;
    private double thirtyRSI;

    private double fiveAverageVolume;
    private double tenAverageVolume;
    private double fifteenAverageVolume;
    private double twentyAverageVolume;
    private double twentyFiveAverageVolume;
    private double thirtyAverageVolume;

    private double fiveVOL;
    private double tenVOL;
    private double fifteenVOL;
    private double twentyVOL;
    private double twentyFiveVOL;
    private double thirtyVOL;

    private double moneyVolume;
    private double fiveMVSMA;
    private double tenMVSMA;
    private double fifteenMVSMA;
    private double twentyMVSMA;
    private double twentyFiveMVSMA;
    private double thirtyMVSMA;

    private int thrustDirection;
    private double thrustFiveEMA;
    private double thrustThirtyEMA;

    private double MACDLine;
    private double MACDSignal;

    private double resistance;
    private double support;

    /**
     * calculated only for market
     */
    private double VIX;

    private boolean isRising = false;

    public Day(LocalDate date) {
        this.date = date;
    }

    /**
     * days are equal of date are the same
     * @param obj to compare
     * @return comparison of objects
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Day) {
            Day compareDay = (Day) obj;
            if (date == null || compareDay.getDate() == null) {
                return false;
            }
            return date.equals(compareDay.getDate());
        }
        throw new ClassCastException("Object to compare can be only Day type");
    }

    @Override
    public int compareTo(Day d2) {
        if(getDate() == d2.getDate()){
            return 0;
        }
        return getDate().compareTo(d2.getDate());
    }
}
