package com.stocker.data.job.market;

import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;

import java.util.List;

public class CalculateBreadthThrust implements IMarketCalculateJob {
    @Override
    public void calculate(Market market, List<Day> days) {
        if (!days.isEmpty()) {
            long advancing = days.stream().filter(Day::isRising).count();
            double breadthThrust = (double) advancing / days.size();
            market.getDays().get(market.getDays().size() - 1).setThrustDirection(breadthThrust);
        }
    }
}
