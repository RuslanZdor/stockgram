package com.stocker.data.job.market;

import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public class CalculateAllMarketFields implements IMarketCalculateJob {

    private final IMarketCalculateJob calculateBreadthThrustSMA;

    @Inject
    public CalculateAllMarketFields(@Named("breadthThrustSMA") IMarketCalculateJob calculateBreadthThrustSMA) {
        this.calculateBreadthThrustSMA = calculateBreadthThrustSMA;
    }

    @Override
    public void calculate(Market market, List<Day> days) {
        calculateBreadthThrustSMA.calculate(market, days);
    }
}
