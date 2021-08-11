package com.stocker.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
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
public class StockDAO {

    private static final DynamoDBMapper mapper = new DynamoDBMapper(DynamoClientFactory.getClient());

    public List<Stock> getAllStocks() {
        return mapper.scan(Stock.class, new DynamoDBScanExpression());
    }

    public void save(Stock stock) {
        mapper.save(stock);
    }

    public void deleteAll() {
        getAllStocks().forEach(mapper::delete);
    }

    public Optional<Day> findLastStockDay(Stock stock) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":symbol", new AttributeValue().withS(stock.getSymbol()));

        DynamoDBQueryExpression<Day> queryExpression = new DynamoDBQueryExpression<Day>()
                .withKeyConditionExpression("symbol = :symbol order by date desc limit 1").withExpressionAttributeValues(eav);

        List<Day> latestReplies = mapper.query(Day.class, queryExpression);
        return latestReplies.stream().findFirst();
    }
}
