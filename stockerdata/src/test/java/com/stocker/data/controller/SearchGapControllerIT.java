package com.stocker.data.controller;

import com.stocker.data.StockDataConfigurationForTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class SearchGapControllerIT {

    @Autowired
    private SearchGapController searchGapController;

    @Test
    public void findCompany() throws InterruptedException {
        searchGapController.findCompany("AAPL");
        Thread.sleep(60000);
    }
}