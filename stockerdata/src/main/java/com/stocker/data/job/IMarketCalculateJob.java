package com.stocker.data.job;

import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;

import java.util.List;


/**
 * Interface for calculation daily parameter for company
 */
public interface IMarketCalculateJob {
    void calculate(Market market, List<Day> days);
}
