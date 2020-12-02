package com.stocker.yahoo.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "strategyResults")
public class StrategyResult {
    @DynamoDBAttribute(attributeName="strategyName")
    private String strategyName;
    @DynamoDBAttribute(attributeName="symbol")
    private String symbol;

}
