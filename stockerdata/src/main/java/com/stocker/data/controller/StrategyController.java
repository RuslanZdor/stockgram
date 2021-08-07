package com.stocker.data.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST API controller for Strategy objects
 */
@Slf4j
@RequiredArgsConstructor
public class StrategyController {
//
//    private final StrategyResultRepository strategyResultRepository;
//
//    /**
//     * load strategy results by strategy name
//     * @param strategyName to search
//     * @return List of Strategy results
//     * @throws IllegalArgumentException in case of not valid name or empty results
//     */
//    @GetMapping("/strategy/{strategyName}")
//    public Flux<StrategyResult> dividendAristocrats(@PathVariable("strategyName") String strategyName) {
//        if (StringUtils.isBlank(strategyName)) {
//            throw new IllegalArgumentException("Strategy name cannot be blank");
//        }
//        return strategyResultRepository.findAllByStrategyName(Mono.just(strategyName)).switchIfEmpty(Mono.error(new IllegalArgumentException(String.format("No results for strategy name %s", strategyName))));
//    }

}
