package com.stocker.yahoo.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * daily information
 */

@Data
@NoArgsConstructor
public class Day implements Comparable<Day>{

    @DynamoDBAttribute(attributeName="symbol")
    private int id;
    @DynamoDBAttribute(attributeName="date")
    private LocalDate date;
    @DynamoDBAttribute(attributeName="last_update")
    private LocalDateTime lastUpdate;
    @DynamoDBAttribute(attributeName="volume")
    private long volume;
    @DynamoDBAttribute(attributeName="price")
    private double price;
    @DynamoDBAttribute(attributeName="min_price")
    private double minPrice;
    @DynamoDBAttribute(attributeName="max_price")
    private double maxPrice;
    @DynamoDBAttribute(attributeName="open_price")
    private double openPrice;
    @DynamoDBAttribute(attributeName="close_price")
    private double closePrice;
    @DynamoDBAttribute(attributeName="EMA5")
    private double EMA5;
    @DynamoDBAttribute(attributeName="EMA10")
    private double EMA10;
    @DynamoDBAttribute(attributeName="EMA20")
    private double EMA20;
    @DynamoDBAttribute(attributeName="EMA50")
    private double EMA50;
    @DynamoDBAttribute(attributeName="EMA200")
    private double EMA200;
    @DynamoDBAttribute(attributeName="SMA5")
    private double SMA5;
    @DynamoDBAttribute(attributeName="SMA10")
    private double SMA10;
    @DynamoDBAttribute(attributeName="SMA20")
    private double SMA20;
    @DynamoDBAttribute(attributeName="SMA50")
    private double SMA50;
    @DynamoDBAttribute(attributeName="SMA200")
    private double SMA200;
    @DynamoDBAttribute(attributeName="RSI5")
    private double RSI5;
    @DynamoDBAttribute(attributeName="RSI10")
    private double RSI10;
    @DynamoDBAttribute(attributeName="RSI20")
    private double RSI20;
    @DynamoDBAttribute(attributeName="RSI50")
    private double RSI50;
    @DynamoDBAttribute(attributeName="RSI200")
    private double RSI200;
    @DynamoDBAttribute(attributeName="RSIGain5")
    private double RSIGain5;
    @DynamoDBAttribute(attributeName="RSIGain10")
    private double RSIGain10;
    @DynamoDBAttribute(attributeName="RSIGain20")
    private double RSIGain20;
    @DynamoDBAttribute(attributeName="RSIGain50")
    private double RSIGain50;
    @DynamoDBAttribute(attributeName="RSIGain200")
    private double RSIGain200;
    @DynamoDBAttribute(attributeName="RSILoss5")
    private double RSILoss5;
    @DynamoDBAttribute(attributeName="RSILoss10")
    private double RSILoss10;
    @DynamoDBAttribute(attributeName="RSILoss20")
    private double RSILoss20;
    @DynamoDBAttribute(attributeName="RSILoss50")
    private double RSILoss50;
    @DynamoDBAttribute(attributeName="RSILoss200")
    private double RSILoss200;
    @DynamoDBAttribute(attributeName="averageVolume5")
    private double averageVolume5;
    @DynamoDBAttribute(attributeName="averageVolume10")
    private double averageVolume10;
    @DynamoDBAttribute(attributeName="averageVolume20")
    private double averageVolume20;
    @DynamoDBAttribute(attributeName="averageVolume50")
    private double averageVolume50;
    @DynamoDBAttribute(attributeName="averageVolume200")
    private double averageVolume200;
    @DynamoDBAttribute(attributeName="VOL5")
    private double VOL5;
    @DynamoDBAttribute(attributeName="VOL10")
    private double VOL10;
    @DynamoDBAttribute(attributeName="VOL20")
    private double VOL20;
    @DynamoDBAttribute(attributeName="VOL50")
    private double VOL50;
    @DynamoDBAttribute(attributeName="VOL200")
    private double VOL200;
    @DynamoDBAttribute(attributeName="moneyVolume")
    private double moneyVolume;
    @DynamoDBAttribute(attributeName="MVSMA5")
    private double MVSMA5;
    @DynamoDBAttribute(attributeName="MVSMA10")
    private double MVSMA10;
    @DynamoDBAttribute(attributeName="MVSMA20")
    private double MVSMA20;
    @DynamoDBAttribute(attributeName="MVSMA50")
    private double MVSMA50;
    @DynamoDBAttribute(attributeName="MVSMA200")
    private double MVSMA200;
    @DynamoDBAttribute(attributeName="thrustDirection")
    private int thrustDirection;
    @DynamoDBAttribute(attributeName="thrustFiveEMA")
    private double thrustFiveEMA;
    @DynamoDBAttribute(attributeName="thrustThirtyEMA")
    private double thrustThirtyEMA;
    @DynamoDBAttribute(attributeName="MACDLine")
    private double MACDLine;
    @DynamoDBAttribute(attributeName="MACDSignal")
    private double MACDSignal;
    @DynamoDBAttribute(attributeName="resistance")
    private double resistance;
    @DynamoDBAttribute(attributeName="support")
    private double support;
    @DynamoDBAttribute(attributeName="isRising")
    private boolean isRising = false;
    @DynamoDBAttribute(attributeName="isNextRise")
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
