package com.stocker.yahoo.parser;

import com.stocker.yahoo.spring.StockYahooConfigurationForTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockYahooConfigurationForTest.class})
public class YahooParserControllerIT {

    @Autowired
    private YahooParserController yahooParserController;

    @Test
    public void reloadStocks() throws InterruptedException {
        yahooParserController.reloadStocks();
        Thread.sleep(300000);
    }

}