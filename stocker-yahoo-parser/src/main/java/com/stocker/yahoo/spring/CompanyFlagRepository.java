package com.stocker.yahoo.spring;

import reactor.core.publisher.Mono;

public interface CompanyFlagRepository<CompanyFlag, String> {
    Mono<CompanyFlag> findFirstBySymbol(Mono<String> symbol);
}
