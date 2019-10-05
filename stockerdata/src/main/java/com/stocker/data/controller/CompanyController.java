package com.stocker.data.controller;

import com.stocker.data.CompanyUtils;
import com.stocker.data.spring.repo.CompanyRepository;
import com.stocker.data.spring.repo.ViewCompanyRepository;
import com.stocker.data.spring.client.YahooDataClient;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.ViewCompany;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
class CompanyController {

    @Autowired
    private ViewCompanyRepository viewCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private YahooDataClient yahooDataClient;

    @GetMapping("/company/{symbol}/")
    public Publisher<Company> findCompany(@PathVariable("symbol") String symbol) {
        Company company = companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).block();
        if (CompanyUtils.needUpdate(company)) {
            yahooDataClient.updateCompany(symbol).block();
        }
        return companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase()));
    }

    @GetMapping("/view/{symbol}/")
    public Publisher<ViewCompany> viewCompany(@PathVariable("symbol") String symbol) {
        Company company = companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).block();
        if (CompanyUtils.needUpdate(company)) {
            yahooDataClient.updateCompany(symbol).block();
        }
        return viewCompanyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase()));
    }

    @PostMapping(path = "/company/{symbol}/")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody Company company) {
        companyRepository.save(company);
    }

}
