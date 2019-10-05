package com.stocker.data.spring.repo;

import com.stocker.yahoo.data.ViewCompany;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ViewCompanyRepository extends ReactiveCrudRepository<ViewCompany, String> {

    Mono<ViewCompany> findFirstBySymbol(Mono<String> symbol);
}
