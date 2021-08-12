package com.stocker.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.stocker.dynamo.DynamoClientFactory;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO pattern for Stocks table
 */
public class DayDAO {

    private static final DynamoDBMapper mapper = new DynamoDBMapper(DynamoClientFactory.getClient());

    public void save(Day day) {
        mapper.save(day);
    }

    public Optional<Day> findLastStockDay(Stock stock) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":symbol", new AttributeValue().withS(stock.getSymbol()));

        DynamoDBQueryExpression<Day> queryExpression = new DynamoDBQueryExpression<Day>()
                .withKeyConditionExpression("symbol = :symbol").withExpressionAttributeValues(eav)
                .withLimit(1);

        List<Day> latestReplies = mapper.query(Day.class, queryExpression);
        return latestReplies.stream().findFirst();
    }
}
