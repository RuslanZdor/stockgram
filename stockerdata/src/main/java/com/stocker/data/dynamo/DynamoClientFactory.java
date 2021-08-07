package com.stocker.data.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamoClientFactory {

    private static AmazonDynamoDB dynamodbClient;

    public static AmazonDynamoDB getClient() {
        if (null != dynamodbClient) {
            return dynamodbClient;
        }

        dynamodbClient = AmazonDynamoDBClientBuilder.standard().build();

        return dynamodbClient;
    }

}
