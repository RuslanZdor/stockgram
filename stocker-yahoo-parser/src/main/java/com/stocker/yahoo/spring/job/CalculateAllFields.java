package com.stocker.yahoo.spring.job;

import com.stocker.yahoo.data.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateAllFields implements ICalculateJob {

    private final ICalculateJob calculateSMA;
    private final ICalculateJob calculateEMA;
    private final ICalculateJob calculateAverageVolume;
    private final ICalculateJob calculateRSI;
    private final ICalculateJob calculateMACDLine;
    private final ICalculateJob calculateMACDSignal;
    private final ICalculateJob calculateRising;
    private final ICalculateJob calculateNextRising;

    @Override
    public void calculate(Company company) {
        calculateSMA.calculate(company);
        calculateEMA.calculate(company);
        calculateAverageVolume.calculate(company);
        calculateRSI.calculate(company);
        calculateMACDLine.calculate(company);
        calculateMACDSignal.calculate(company);
        calculateRising.calculate(company);
        calculateNextRising.calculate(company);
    }
}
