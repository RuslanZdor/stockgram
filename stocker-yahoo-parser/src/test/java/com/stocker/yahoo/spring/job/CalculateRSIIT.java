package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.spring.CompanyRepository;
import com.stocker.yahoo.spring.StockYahooConfigurationForTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockYahooConfigurationForTest.class})
public class CalculateRSIIT {

//    @Autowired
//    private CompanyRepository companyRepository;

    @Autowired
    private CalculateRSI calculateRSI;

    @Test
    public void calculate() {
//        companyRepository.findFirstBySymbol(Mono.just("AAPL")).subscribe(company -> {
//            log.info(company.getSymbol());
//            calculateRSI.calculate(company);
//            assertTrue(company.getDays().last().getRSI200() != 0.0);
//        });
    }
}