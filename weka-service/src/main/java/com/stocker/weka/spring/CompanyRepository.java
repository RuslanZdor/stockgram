package com.stocker.weka.spring;

import com.stocker.yahoo.data.Company;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CompanyRepository extends ReactiveCrudRepository<Company, String> {
    Mono<Company> findFirstBySymbol(Mono<String> symbol);

    @Query("{\"companyStats.sharesOutstanding\": {$gt: ?0} }")
    Flux<Company> findByBigCap(long cap);
}
