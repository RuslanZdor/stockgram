package com.stocker.telegram.spring.client;

import com.stocker.spring.CompanyDataClient;
import com.stocker.telegram.spring.StockConfigurationForTest;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockConfigurationForTest.class})
public class StockerDataClientIT {

    @Autowired
    private CompanyDataClient companyDataClient;
    @Test
    public void getCompany() {
        Mono<Company> testCompany = companyDataClient.getCompany("aapl");

        StepVerifier.create(testCompany)
                .expectNextMatches(company -> company.getSymbol().equalsIgnoreCase("aapl"))
                .verifyComplete();
    }
}