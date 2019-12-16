package com.stocker.data.controller;

import com.stocker.data.spring.repo.StrategyResultRepository;
import com.stocker.yahoo.data.StrategyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST API controller for Strategy objects
 */
@Slf4j
@RestController
public class StrategyController {

    @Autowired
    private StrategyResultRepository strategyResultRepository;

    /**
     * load strategy results by strategy name
     * @param strategyName to search
     * @return List of Strategy results
     * @throws IllegalArgumentException in case of not valid name or empty results
     */
    @GetMapping("/strategy/{strategyName}")
    public Flux<StrategyResult> dividendAristocrats(@PathVariable("strategyName") String strategyName) {
        if (StringUtils.isBlank(strategyName)) {
            throw new IllegalArgumentException("Strategy name cannot be blank");
        }
        return strategyResultRepository.findAllByStrategyName(Mono.just(strategyName)).switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("No results for strategy name %s", strategyName))));
    }

}
