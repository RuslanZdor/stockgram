package com.stocker.data.controller;

import com.stocker.data.StockDataConfigurationForTest;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class CompanyControllerIT {

    @Autowired
    private CompanyController companyController;

    @Test
    public void findCompany() {
        Company company = companyController.findCompany("AAPL").block();
        Objects.requireNonNull(company);
        assertEquals("Request have to find AAPL company", "AAPL", company.getSymbol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyEmptyResult() {
        companyController.findCompany("wrongCompany").block();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyEmptySymbol() {
        companyController.findCompany(" ").block();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findCompanyNullSymbol() {
        companyController.findCompany(null).block();
    }

    @Test
    public void viewCompany() {
        Company company = companyController.findCompany("AAPL").block();
        Objects.requireNonNull(company);
        assertEquals("Request have to find AAPL company", "AAPL", company.getSymbol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewCompanyEmptyResult() {
        companyController.viewCompany("wrongCompany").block();
    }
}