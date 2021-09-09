package com.stocker.data.job.stock;

import com.stocker.data.job.CalculationsUtil;
import com.stocker.data.job.ICalculateJob;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

/**
 * Calculate simple moving average for company/market/industry
 */
public class CalculateSMA implements ICalculateJob {

    public void calculate(Company company) {
        CalculationsUtil.calculateSMA(company.getDays(), 5, Day::getPrice, Day::setSMA5);
        CalculationsUtil.calculateSMA(company.getDays(), 10, Day::getPrice, Day::setSMA10);
        CalculationsUtil.calculateSMA(company.getDays(), 20, Day::getPrice, Day::setSMA20);
        CalculationsUtil.calculateSMA(company.getDays(), 50, Day::getPrice, Day::setSMA50);
        CalculationsUtil.calculateSMA(company.getDays(), 200, Day::getPrice, Day::setSMA200);
    }
}
