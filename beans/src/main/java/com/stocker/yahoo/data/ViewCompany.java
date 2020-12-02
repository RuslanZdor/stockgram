package com.stocker.yahoo.data;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "company")
public class ViewCompany {

    @DynamoDBAttribute(attributeName="id")
    private String id;
    @DynamoDBAttribute(attributeName="name")
    private String name;
    @DynamoDBAttribute(attributeName="symbol")
    private String symbol;
    @DynamoDBAttribute(attributeName="industry")
    private String industry;
    @DynamoDBAttribute(attributeName="companyStats")
    private CompanyStats companyStats;
}
