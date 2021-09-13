package com.stocker.data.job.market;

import com.stocker.data.job.CalculationsUtil;
import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;
import com.stocker.yahoo.data.market.MarketDay;

import java.util.List;

public class CalculateSMABreadthThrust implements IMarketCalculateJob {
    @Override
    public void calculate(Market market, List<Day> days) {
        CalculationsUtil.calculateMarketSMA(market.getDays(),
                10,
                MarketDay::getThrustDirection,
                MarketDay::setThrustTenSMA);
        CalculationsUtil.calculateMarketSMA(market.getDays(),
                30,
                MarketDay::getThrustDirection,
                MarketDay::setThrustThirtySMA);
    }
}
