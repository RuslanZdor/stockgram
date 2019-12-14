package com.stocker.strategic.spring;

import com.stocker.yahoo.data.Company;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, String> {

    @Query(value = "{'dividends.99': {$exists: true} }")
    Flux<Company> findLongDividendsHistory();
}
