package com.stocker.strategic.spring.controller;

import com.stocker.strategic.spring.StrategyResultRepository;
import com.stocker.strategic.spring.calculation.DividendAristocratsCalculation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
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
