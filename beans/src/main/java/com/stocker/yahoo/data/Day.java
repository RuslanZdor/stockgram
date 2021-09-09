package com.stocker.yahoo.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * daily information
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName="stock_daily_values")
public class Day implements Comparable<Day>{

    @DynamoDBHashKey(attributeName="symbol")
    private String symbol;
    @DynamoDBRangeKey(attributeName="date")
    private long date;
    @DynamoDBAttribute(attributeName="is_finished")
    private boolean isFinished;
    @DynamoDBAttribute(attributeName="last_update")
    private long lastUpdate;
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
    @DynamoDBAttribute(attributeName="ema_5")
    private double EMA5;
    @DynamoDBAttribute(attributeName="ema_10")
    private double EMA10;
    @DynamoDBAttribute(attributeName="ema_20")
    private double EMA20;
    @DynamoDBAttribute(attributeName="ema_50")
    private double EMA50;
    @DynamoDBAttribute(attributeName="ema_200")
    private double EMA200;
    @DynamoDBAttribute(attributeName="sma_5")
    private double SMA5;
    @DynamoDBAttribute(attributeName="sma_10")
    private double SMA10;
    @DynamoDBAttribute(attributeName="sma_20")
    private double SMA20;
    @DynamoDBAttribute(attributeName="sma_50")
    private double SMA50;
    @DynamoDBAttribute(attributeName="sma_200")
    private double SMA200;
    @DynamoDBAttribute(attributeName="rsi_5")
    private double RSI5;
    @DynamoDBAttribute(attributeName="rsi_10")
    private double RSI10;
    @DynamoDBAttribute(attributeName="rsi_20")
    private double RSI20;
    @DynamoDBAttribute(attributeName="rsi_50")
    private double RSI50;
    @DynamoDBAttribute(attributeName="rsi_200")
    private double RSI200;
    @DynamoDBAttribute(attributeName="rsi_gain_5")
    private double RSIGain5;
    @DynamoDBAttribute(attributeName="rsi_gain_10")
    private double RSIGain10;
    @DynamoDBAttribute(attributeName="rsi_gain_20")
    private double RSIGain20;
    @DynamoDBAttribute(attributeName="rsi_gain_50")
    private double RSIGain50;
    @DynamoDBAttribute(attributeName="rsi_gain_200")
    private double RSIGain200;
    @DynamoDBAttribute(attributeName="rsi_loss_5")
    private double RSILoss5;
    @DynamoDBAttribute(attributeName="rsi_loss_10")
    private double RSILoss10;
    @DynamoDBAttribute(attributeName="rsi_loss_20")
    private double RSILoss20;
    @DynamoDBAttribute(attributeName="rsi_loss_50")
    private double RSILoss50;
    @DynamoDBAttribute(attributeName="rsi_loss_200")
    private double RSILoss200;
    @DynamoDBAttribute(attributeName="average_volume_5")
    private double averageVolume5;
    @DynamoDBAttribute(attributeName="average_volume_10")
    private double averageVolume10;
    @DynamoDBAttribute(attributeName="average_volume_20")
    private double averageVolume20;
    @DynamoDBAttribute(attributeName="average_volume_50")
    private double averageVolume50;
    @DynamoDBAttribute(attributeName="average_volume_200")
    private double averageVolume200;
    @DynamoDBAttribute(attributeName="vol_5")
    private double VOL5;
    @DynamoDBAttribute(attributeName="vol_10")
    private double VOL10;
    @DynamoDBAttribute(attributeName="vol_20")
    private double VOL20;
    @DynamoDBAttribute(attributeName="vol_50")
    private double VOL50;
    @DynamoDBAttribute(attributeName="vol_200")
    private double VOL200;
    @DynamoDBAttribute(attributeName="thrust_direction")
    private int thrustDirection;
    @DynamoDBAttribute(attributeName="thrust_five_ema")
    private double thrustFiveEMA;
    @DynamoDBAttribute(attributeName="thrust_thirty_ema")
    private double thrustThirtyEMA;
    @DynamoDBAttribute(attributeName="macd_line")
    private double MACDLine;
    @DynamoDBAttribute(attributeName="macd_signal")
    private double MACDSignal;
    @DynamoDBAttribute(attributeName="resistance")
    private double resistance;
    @DynamoDBAttribute(attributeName="support")
    private double support;
    @DynamoDBAttribute(attributeName="is_rising")
    private boolean isRising = false;
    @DynamoDBAttribute(attributeName="is_next_rise")
    private boolean isNextRise = false;

    public Day(long date) {
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
            return date == compareDay.getDate();
        }
        throw new ClassCastException("Object to compare can be only Day type");
    }

    @Override
    public int compareTo(Day d2) {
        if(getDate() == d2.getDate()){
            return 0;
        }
        return Long.compare(getDate(), d2.getDate());
    }
}
