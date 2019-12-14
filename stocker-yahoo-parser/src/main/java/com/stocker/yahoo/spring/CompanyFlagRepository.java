package com.stocker.yahoo.spring;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.CompanyFlag;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyFlagRepository extends ReactiveCrudRepository<CompanyFlag, String> {
    Mono<CompanyFlag> findFirstBySymbol(Mono<String> symbol);
}
