package com.stocker.yahoo.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dividend implements Comparable<Dividend> {
    @DynamoDBAttribute(attributeName="date")
    private long date;
    @DynamoDBAttribute(attributeName="value")
    private double value;

    /**
     * days are equal of date are the same
     * @param obj to compare
     * @return comparison of objects
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Day) {
            Day compareDay = (Day) obj;
            return date == compareDay.getDateTimestamp();
        }
        throw new ClassCastException("Object to compare can be only Day type");
    }

    @Override
    public int compareTo(Dividend d2) {
        if(getDate() == d2.getDate()){
            return 0;
        }
        return Long.compare(getDate(), d2.getDate());
    }
}
