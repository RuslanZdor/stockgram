package com.stocker.yahoo.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NoArgsConstructor
public class CompanyStats {

    @DynamoDBAttribute(attributeName="market_cap")
    private double marketCap;
    @DynamoDBAttribute(attributeName="shares_float")
    private long sharesFloat;
    @DynamoDBAttribute(attributeName="shares_outstanding")
    private long sharesOutstanding;
    @DynamoDBAttribute(attributeName="shares_owned")
    private long sharesOwned;
    @DynamoDBAttribute(attributeName="eps")
    private double eps;
    @DynamoDBAttribute(attributeName="pe")
    private double pe;
    @DynamoDBAttribute(attributeName="peg")
    private double peg;
    @DynamoDBAttribute(attributeName="epsEstimate_current_year")
    private double epsEstimateCurrentYear;
    @DynamoDBAttribute(attributeName="eps_estimate_next_quarter")
    private double epsEstimateNextQuarter;
    @DynamoDBAttribute(attributeName="eps_estimate_next_year")
    private double epsEstimateNextYear;
    @DynamoDBAttribute(attributeName="price_book")
    private double priceBook;
    @DynamoDBAttribute(attributeName="price_sales")
    private double priceSales;
    @DynamoDBAttribute(attributeName="book_value_per_share")
    private double bookValuePerShare;
    @DynamoDBAttribute(attributeName="revenue")
    private double revenue;
    @DynamoDBAttribute(attributeName="EBITDA")
    private double EBITDA;
    @DynamoDBAttribute(attributeName="one_year_target_price")
    private double oneYearTargetPrice;
    @DynamoDBAttribute(attributeName="short_ratio")
    private double shortRatio;
    @DynamoDBAttribute(attributeName="earnings_announcement")
    private long earningsAnnouncement;
    @DynamoDBAttribute(attributeName="last_price")
    private double lastPrice;
    @DynamoDBAttribute(attributeName="last_day_open_price")
    private double lastDayOpenPrice;
    @DynamoDBAttribute(attributeName="last_day_close_price")
    private double lastDayClosePrice;
    @DynamoDBAttribute(attributeName="last_update")
    private long lastUpdate;

}
