package com.stocker.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.stocker.dynamo.DynamoClientFactory;
import com.stocker.yahoo.data.market.MarketDay;
import com.stocker.yahoo.data.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO pattern for Stocks table
 */
public class MarketDayDAO {

    private static final DynamoDBMapper mapper = new DynamoDBMapper(DynamoClientFactory.getClient());

    public void save(MarketDay day) {
        mapper.save(day);
    }

    public Optional<MarketDay> findLastStockDay(Stock stock) {
        return findLastStockDay(stock.getSymbol());
    }

    public Optional<MarketDay> findLastStockDay(String stock) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":symbol", new AttributeValue().withS(stock));

        DynamoDBQueryExpression<MarketDay> queryExpression = new DynamoDBQueryExpression<MarketDay>()
                .withKeyConditionExpression("symbol = :symbol")
                .withExpressionAttributeValues(eav)
                .withLimit(1);

        List<MarketDay> latestReplies = mapper.query(MarketDay.class, queryExpression);
        return latestReplies.stream().findFirst();
    }

    public List<MarketDay> findAllData(String symbol) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":symbol", new AttributeValue().withS(symbol));

        DynamoDBQueryExpression<MarketDay> queryExpression = new DynamoDBQueryExpression<MarketDay>()
                .withKeyConditionExpression("symbol = :symbol")
                .withExpressionAttributeValues(eav);

        return mapper.query(MarketDay.class, queryExpression);
    }

    public void delete(MarketDay day) {
        mapper.delete(day);
    }
}
