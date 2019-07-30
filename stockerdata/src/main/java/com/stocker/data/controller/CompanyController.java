package com.stocker.data.controller;

import com.stocker.data.spring.CompanyRepository;
import com.stocker.yahoo.data.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

    @GetMapping("/company/{symbol}/")
    public Publisher<Company> findCompany(@PathVariable("symbol") String symbol) {
        return repository.findFirstBySymbol(Mono.just(symbol.toUpperCase()));
    }

    @PostMapping(path = "/company/{symbol}/")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCompany(@RequestBody Company company) {
        repository.save(company);
    }

}
