package com.stocker.data.job.market;

import com.stocker.yahoo.data.market.Market;
import com.stocker.yahoo.data.market.MarketDay;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CalculateSMABreadthThrustTest {

    private CalculateSMABreadthThrust calculateSMA;

    @Before
    public void init() {
        calculateSMA = new CalculateSMABreadthThrust();
    }

    @Test
    public void calculate() {
        Market market = new Market();
        for (int i = 1; i <= 201; i++) {
            MarketDay day = new MarketDay(LocalDate.now().plus(i, ChronoUnit.DAYS).toEpochDay());
            day.setThrustDirection(i);
            market.getDays().add(day);
        }

        calculateSMA.calculate(market, new ArrayList<>());

        assertEquals(196.5, market.getDays().get(200).getThrustTenSMA(), 0.1);
        assertEquals(186.5, market.getDays().get(200).getThrustThirtySMA(), 0.1);
    }

    @Test
    public void firstDay() {
        Market market = new Market();
        MarketDay day = new MarketDay(LocalDate.now().toEpochDay());
        day.setThrustDirection(10);
        market.getDays().add(day);

        calculateSMA.calculate(market, new ArrayList<>());

        assertEquals(10, market.getDays().get(0).getThrustTenSMA(), 0.1);
        assertEquals(10, market.getDays().get(0).getThrustThirtySMA(), 0.1);
    }
}