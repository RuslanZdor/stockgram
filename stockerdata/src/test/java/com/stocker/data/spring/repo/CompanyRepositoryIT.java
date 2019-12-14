package com.stocker.data.spring.repo;

import com.stocker.data.StockDataConfigurationForTest;
import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class CompanyRepositoryIT {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void findFirstByTelegramId() {
        Company company = companyRepository.findFirstBySymbol(Mono.just("AAPL")).single().block();
        Objects.requireNonNull(company);
        assertEquals("AAPL", company.getSymbol());
        assertFalse(company.getDays().isEmpty());
    }
}