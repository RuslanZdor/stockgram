package com.stocker.data.controller;

import com.stocker.data.spring.repo.CompanyRepository;
import com.stocker.data.spring.repo.ViewCompanyRepository;
import com.stocker.spring.client.YahooDataClient;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.ViewCompany;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * API REST controller to handle company requests
 */
@Slf4j
@RestController
@RequiredArgsConstructor
class CompanyController {

    private final ViewCompanyRepository viewCompanyRepository;
    private final CompanyRepository companyRepository;
    private final YahooDataClient yahooDataClient;

    /**
     * Get all company information in json format
     * @param symbol to search a company
     * @return company object
     * @throws IllegalArgumentException in case of no results
     */
    @GetMapping("/company/{symbol}/")
    public Mono<Company> findCompany(@PathVariable("symbol") String symbol) throws IllegalArgumentException {
        callCompanyUpdate(symbol);
        return companyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("No result for symbol %s", symbol))));
    }

    /**
     * Get company information without history
     * @param symbol to search
     * @return ViewCompany Object
     * @throws IllegalArgumentException in case of no results
     */
    @GetMapping("/view/{symbol}/")
    public Mono<ViewCompany> viewCompany(@PathVariable("symbol") String symbol) {
        callCompanyUpdate(symbol);
        return viewCompanyRepository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("No result for symbol %s", symbol))));
    }

    @PostMapping(path = "/company/{symbol}/")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody Company company) {
        companyRepository.save(company);
    }

    /**
     * call yahoo service to update company information
     * @param symbol to update company
     */
    private void callCompanyUpdate(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            throw new IllegalArgumentException("Symbol cannot be empty");
        }
        if (yahooDataClient.isAvailable()) {
            try {
                yahooDataClient.updateCompany(symbol).block();
            } catch (Exception ex) {
                log.error("Failed to call yahoo service to update company", ex);
            }
        }
    }
}
