package com.stocker.yahoo.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * daily information
 */

@Data
@NoArgsConstructor
@Document
public class Day implements Comparable<Day>{

    @Id
    private int id;

    private LocalDate date;

    private long volume;

    private double price;
    private double minPrice;
    private double maxPrice;
    private double openPrice;

    private double EMA5;
    private double EMA10;
    private double EMA20;
    private double EMA50;
    private double EMA200;

    private double SMA5;
    private double SMA10;
    private double SMA20;
    private double SMA50;
    private double SMA200;

    private double RSI5;
    private double RSI10;
    private double RSI20;
    private double RSI50;
    private double RSI200;

    private double RSIGain5;
    private double RSIGain10;
    private double RSIGain20;
    private double RSIGain50;
    private double RSIGain200;

    private double RSILoss5;
    private double RSILoss10;
    private double RSILoss20;
    private double RSILoss50;
    private double RSILoss200;

    private double averageVolume5;
    private double averageVolume10;
    private double averageVolume20;
    private double averageVolume50;
    private double averageVolume200;

    private double VOL5;
    private double VOL10;
    private double VOL20;
    private double VOL50;
    private double VOL200;

    private double moneyVolume;

    private double MVSMA5;
    private double MVSMA10;
    private double MVSMA20;
    private double MVSMA50;
    private double MVSMA200;

    private int thrustDirection;
    private double thrustFiveEMA;
    private double thrustThirtyEMA;

    private double MACDLine;
    private double MACDSignal;

    private double resistance;
    private double support;

    private boolean isRising = false;

    private boolean isNextRise = false;

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
            return date != null && compareDay.getDate() != null && date.equals(compareDay.getDate());
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
