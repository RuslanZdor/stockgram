package com.stocker.strategic.spring.controller;

import com.stocker.strategic.spring.StrategyResultRepository;
import com.stocker.strategic.spring.calculation.DividendAristocratsCalculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
class StrategyController {

    @Autowired
    private DividendAristocratsCalculation dividendAristocratsCalculation;

    @Autowired
    private StrategyResultRepository strategyResultRepository;

    @GetMapping("/strategy/dividendAristocrats/update")
    public void updateDividendAristocrats() {
        dividendAristocratsCalculation.calculate();
    }

}
