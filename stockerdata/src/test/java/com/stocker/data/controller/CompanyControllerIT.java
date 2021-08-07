package com.stocker.data.controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanyControllerIT {
//
//    @Autowired
//    private CompanyController companyController;
//
//    @Test
//    public void findCompany() {
//        Company company = companyController.findCompany("AAPL").block();
//        Objects.requireNonNull(company);
//        assertEquals("Request have to find AAPL company", "AAPL", company.getSymbol());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void findCompanyEmptyResult() {
//        companyController.findCompany("wrongCompany").block();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void findCompanyEmptySymbol() {
//        companyController.findCompany(" ").block();
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void findCompanyNullSymbol() {
//        companyController.findCompany(null).block();
//    }
//
//    @Test
//    public void viewCompany() {
//        Company company = companyController.findCompany("AAPL").block();
//        Objects.requireNonNull(company);
//        assertEquals("Request have to find AAPL company", "AAPL", company.getSymbol());
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void viewCompanyEmptyResult() {
//        companyController.viewCompany("wrongCompany").block();
//    }
}