package com.stocker.data.job.market;

import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Market;
import com.stocker.yahoo.data.MarketDay;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalculateBreadthThrustTest {

    private CalculateBreadthThrust calculateBreadthThrust;
    private Market market;

    @Before
    public void init() {
        calculateBreadthThrust = new CalculateBreadthThrust();
        market = Market.builder()
                .days(Collections.singletonList(new MarketDay()))
                .build();
    }

    @Test
    public void calculateAllRising() {
        List<Day> days = Arrays.asList(
                Day.builder()
                        .isRising(true)
                        .build(),
                Day.builder()
                        .isRising(true)
                        .build()
        );

        calculateBreadthThrust.calculate(market, days);
        assertEquals(1, market.getDays().get(0).getThrustDirection(), 0.01);
    }

    @Test
    public void calculateAllFalling() {
        List<Day> days = Arrays.asList(
                Day.builder()
                        .isRising(false)
                        .build(),
                Day.builder()
                        .isRising(false)
                        .build()
        );

        calculateBreadthThrust.calculate(market, days);
        assertEquals(0, market.getDays().get(0).getThrustDirection(), 0.01);
    }

    @Test
    public void calculate() {
        List<Day> days = Arrays.asList(
                Day.builder()
                        .isRising(true)
                        .build(),
                Day.builder()
                        .isRising(false)
                        .build()
        );

        calculateBreadthThrust.calculate(market, days);
        assertEquals(0.5, market.getDays().get(0).getThrustDirection(), 0.01);
    }
}