package com.stocker.data.spring.repo;

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
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class StrategyResultRepositoryIT {

    @Autowired
    private StrategyResultRepository strategyResultRepository;

    @Autowired
    private StockDataProperties stockDataProperties;

    @Test
    public void findResults() {
        StrategyResult result = strategyResultRepository.findByStrategyName(Mono.just(stockDataProperties.getDividendAristocrats())).blockFirst();
        assertNotNull(result);
    }
}