package com.stocker.data.controller;

import com.stocker.data.StockDataConfigurationForTest;
import com.stocker.data.StockDataProperties;
import com.stocker.yahoo.data.StrategyResult;
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
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class StrategyControllerIT {

    @Autowired
    private StrategyController strategyController;

    @Autowired
    private StockDataProperties stockDataProperties;

    @Test
    public void dividendAristocrats() {
        StrategyResult strategyResult = strategyController.dividendAristocrats(stockDataProperties.getDividendAristocrats()).blockFirst();
        assertNotNull("Strategy results cannot be null", strategyResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyEmptyResult() {
        strategyController.dividendAristocrats("wrongCompany").blockFirst();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyEmptySymbol() {
        strategyController.dividendAristocrats(" ").blockFirst();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyNullSymbol() {
        strategyController.dividendAristocrats(null).blockFirst();
    }
}