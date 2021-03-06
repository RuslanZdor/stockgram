package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, String> {
    Mono<Company> findFirstBySymbol(Mono<String> symbol);
}
