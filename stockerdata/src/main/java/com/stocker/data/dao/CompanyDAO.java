package com.stocker.data.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.stocker.dynamo.DynamoClientFactory;
import com.stocker.yahoo.data.Company;

/**
 * DAO pattern for Stocks table
 */
public class CompanyDAO {

    private static final DynamoDBMapper mapper = new DynamoDBMapper(DynamoClientFactory.getClient());
    public void save(Company stock) {
        mapper.save(stock);
    }

}
