package com.stocker.telegram;

import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
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