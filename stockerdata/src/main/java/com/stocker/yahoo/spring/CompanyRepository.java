package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import reactor.core.publisher.Mono;

public interface CompanyRepository {
    Mono<Company> findFirstBySymbol(Mono<String> symbol);
}
