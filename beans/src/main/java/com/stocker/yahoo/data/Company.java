package com.stocker.yahoo.data;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName="company")
public class Company {

    @DynamoDBHashKey(attributeName="id")
    private String id;
    @DynamoDBAttribute(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName="symbol")
    private String symbol;
    @DynamoDBAttribute(attributeName="industry")
    private String industry;
    @DynamoDBAttribute(attributeName="sp500Flag")
    private boolean sp500Flag;

    @DynamoDBAttribute(attributeName="company_stats")
    private CompanyStats companyStats;

    @DynamoDBAttribute(attributeName="days")
    private List<Day> days = new ArrayList<>();
    @DynamoDBAttribute(attributeName="dividends")
    private List<Dividend> dividends = new ArrayList<>();

    /**
     * return day with the same date as in parameter
     * or new day with this date will be created and added to list
     *
     * @param day for search
     * @return founded or created day
     */
    public Day getDay(Day day) {
        return days.stream().filter(fDay -> fDay.getDateTimestamp() == day.getDateTimestamp())
                .findFirst().orElse(days.get(0));
    }

    /**
     * find max daily volume for this company
     * zero in case of empty list
     * @return max volume
     */
    public long getMaxDayVolume() {
        return getDays().stream().map(Day::getVolume).max(Long::compare).orElse(0L);
    }
}
