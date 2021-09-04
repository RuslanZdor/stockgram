package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

/**
 * Calculate Exponential moving average for company/market/industry
 */
public class CalculateEMA implements ICalculateJob {
    public void calculate(Company company) {
        CalculationsUtil.calculateEMA(company.getDays(), 5, Day::setEMA5);
        CalculationsUtil.calculateEMA(company.getDays(), 10, Day::setEMA10);
        CalculationsUtil.calculateEMA(company.getDays(), 20, Day::setEMA20);
        CalculationsUtil.calculateEMA(company.getDays(), 50, Day::setEMA50);
        CalculationsUtil.calculateEMA(company.getDays(), 200, Day::setEMA200);
    }
}
