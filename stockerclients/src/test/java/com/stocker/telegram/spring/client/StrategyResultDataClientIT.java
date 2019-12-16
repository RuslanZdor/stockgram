package com.stocker.telegram.spring.client;

import com.stocker.spring.StrategyResultDataClient;
import com.stocker.telegram.spring.StockConfigurationForTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockConfigurationForTest.class})
public class StrategyResultDataClientIT {

    @Autowired
    private StrategyResultDataClient strategyResultDataClient;

    @Test
    public void getStrategyResult() {
        assertTrue(strategyResultDataClient.getStrategyResult("dividendsAristocrats").count().block() > 0);
    }

    @Test
    public void getStrategyResultShow() {
        strategyResultDataClient.getStrategyResult("dividendsAristocrats").subscribe(strategyResult -> log.info(strategyResult.getSymbol()));
    }

}