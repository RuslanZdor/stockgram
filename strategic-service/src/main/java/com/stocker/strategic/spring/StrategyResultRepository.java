package com.stocker.strategic.spring;

import com.stocker.yahoo.data.StrategyResult;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyResultRepository extends ReactiveCrudRepository<StrategyResult, String> {
}
