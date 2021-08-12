package com.stocker.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.stocker.dynamo.DynamoClientFactory;
import com.stocker.yahoo.data.Stock;

import java.util.List;

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
}
