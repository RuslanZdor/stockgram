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
@DynamoDBTable(tableName="market_daily_values")
public class MarketDay implements Comparable<MarketDay>{

    @DynamoDBHashKey(attributeName="symbol")
    private String symbol;
    @DynamoDBRangeKey(attributeName="date")
    private long date;
    @DynamoDBAttribute(attributeName="is_finished")
    private boolean isFinished;
    @DynamoDBAttribute(attributeName="last_update")
    private long lastUpdate;
    @DynamoDBAttribute(attributeName="thrust_direction")
    private double thrustDirection;
    @DynamoDBAttribute(attributeName="thrust_five_ema")
    private double thrustFiveEMA;
    @DynamoDBAttribute(attributeName="thrust_thirty_ema")
    private double thrustThirtyEMA;

    public MarketDay(long date) {
        this.date = date;
    }

    /**
     * days are equal of date are the same
     * @param obj to compare
     * @return comparison of objects
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MarketDay) {
            MarketDay compareDay = (MarketDay) obj;
            return date == compareDay.getDate();
        }
        throw new ClassCastException("Object to compare can be only Day type");
    }

    @Override
    public int compareTo(MarketDay d2) {
        if(getDate() == d2.getDate()){
            return 0;
        }
        return Long.compare(getDate(), d2.getDate());
    }
}
