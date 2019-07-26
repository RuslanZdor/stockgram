package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.spring.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateRSIIT {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CalculateRSI calculateRSI;

    @Test
    public void calculate() {
        companyRepository.findFirstBySymbol(Mono.just("AAPL")).subscribe(company -> {
            log.info(company.getSymbol());
            calculateRSI.calculate(company);
            assertTrue(company.getDays().last().getThirtyRSI() != 0.0);
        });
    }
}