package com.stocker.data.job.market;

import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public class CalculateAllMarketDailyFields implements IMarketCalculateJob {

    private final IMarketCalculateJob calculateBreadthThrust;

    @Inject
    public CalculateAllMarketDailyFields(@Named("breadthThrust") IMarketCalculateJob calculateBreadthThrust) {
        this.calculateBreadthThrust = calculateBreadthThrust;
    }

    @Override
    public void calculate(Market market, List<Day> days) {
        calculateBreadthThrust.calculate(market, days);
    }
}
