package com.stocker.data.controller;

import com.stocker.data.CompanyUtils;
import com.stocker.data.spring.CompanyRepository;
import com.stocker.data.spring.YahooDataClient;
import com.stocker.yahoo.data.Company;
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
    private CompanyRepository repository;

    @Autowired
    private YahooDataClient yahooDataClient;

    @GetMapping("/company/{symbol}/")
    public Publisher<Company> findCompany(@PathVariable("symbol") String symbol) {
        Company company = repository.findFirstBySymbol(Mono.just(symbol.toUpperCase())).block();
        log.info("need update " + CompanyUtils.needUpdate(company));
        if (CompanyUtils.needUpdate(company)) {
            yahooDataClient.updateCompany(symbol).block();
        }
        return repository.findFirstBySymbol(Mono.just(symbol.toUpperCase()));
    }

    @PostMapping(path = "/company/{symbol}/")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody Company company) {
        repository.save(company);
    }

}
