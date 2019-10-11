package com.stocker.telegram.spring.client;

import com.stocker.telegram.StockTelegramConfigurationForTest;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
public class StrategyResultDataClientIT {

    @Autowired
    private StrategyResultDataClient strategyResultDataClient;

    @Test
    public void getStrategyResult() {
        assertTrue(strategyResultDataClient.getStrategyResult("dividendsAristocrats").count().block() > 0);
    }

}