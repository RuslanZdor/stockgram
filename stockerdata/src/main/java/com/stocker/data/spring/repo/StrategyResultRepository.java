package com.stocker.data.spring.repo;

import com.stocker.yahoo.data.StrategyResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StrategyResultRepository extends ReactiveCrudRepository<StrategyResult, String> {

    Flux<StrategyResult> findByStrategyName(Mono<String> symbol);

    Flux<StrategyResult> findAllByStrategyName(Mono<String> strategyName);

}
