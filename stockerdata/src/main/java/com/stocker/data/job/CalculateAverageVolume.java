package com.stocker.data.job;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;

/**
 * Calculate SMA volume
 */
public class CalculateAverageVolume implements ICalculateJob {
    public void calculate(Company company) {
        CalculationsUtil.calculateSMA(company.getDays(), 5, Day::getVolume, Day::setAverageVolume5);
        CalculationsUtil.calculateSMA(company.getDays(), 10, Day::getVolume, Day::setAverageVolume10);
        CalculationsUtil.calculateSMA(company.getDays(), 20, Day::getVolume, Day::setAverageVolume20);
        CalculationsUtil.calculateSMA(company.getDays(), 50, Day::getVolume, Day::setAverageVolume50);
        CalculationsUtil.calculateSMA(company.getDays(), 200, Day::getVolume, Day::setAverageVolume200);
    }
}
