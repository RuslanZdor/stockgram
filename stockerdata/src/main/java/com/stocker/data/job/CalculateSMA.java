package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

/**
 * Calculate simple moving average for company/market/industry
 */
public class CalculateSMA implements ICalculateJob {

    public void calculate(Company company) {
        CalculationsUtil.calculateSMA(company.getDays(), 5, Day::setSMA5);
        CalculationsUtil.calculateSMA(company.getDays(), 10, Day::setSMA10);
        CalculationsUtil.calculateSMA(company.getDays(), 20, Day::setSMA20);
        CalculationsUtil.calculateSMA(company.getDays(), 50, Day::setSMA50);
        CalculationsUtil.calculateSMA(company.getDays(), 200, Day::setSMA200);
    }
}
