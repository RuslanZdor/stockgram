package com.stocker.weka.spring;

import com.stocker.yahoo.data.Company;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository {
    Mono<Company> findFirstBySymbol(Mono<String> symbol);

    Flux<Company> findByBigCap(long cap);
}
