package com.stocker.data.controller;

import com.stocker.data.spring.repo.StrategyResultRepository;
import com.stocker.yahoo.data.StrategyResult;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
public class StrategyController {

    @Autowired
    private StrategyResultRepository strategyRessultRepository;

    @GetMapping("/strategy/{strategyName}")
    public Publisher<StrategyResult> dividendAristocrats(@PathVariable("strategyName") String strategyName) {
        return strategyRessultRepository.findAllByStrategyName(Mono.just(strategyName));
    }

}
