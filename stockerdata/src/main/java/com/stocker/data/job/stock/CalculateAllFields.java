package com.stocker.data.job.stock;

import com.stocker.data.job.ICalculateJob;
import com.stocker.yahoo.data.Company;

import javax.inject.Inject;
import javax.inject.Named;

public class CalculateAllFields implements ICalculateJob {

    private final ICalculateJob calculateSMA;
    private final ICalculateJob calculateEMA;
    private final ICalculateJob calculateAverageVolume;
    private final ICalculateJob calculateRSI;
    private final ICalculateJob calculateMACDLine;
    private final ICalculateJob calculateMACDSignal;
    private final ICalculateJob calculateRising;
    private final ICalculateJob calculateNextRising;

    @Inject
    public CalculateAllFields(@Named("sma") ICalculateJob calculateSMA,
                              @Named("ema") ICalculateJob calculateEMA,
                              @Named("averageVolume") ICalculateJob calculateAverageVolume,
                              @Named("rsi") ICalculateJob calculateRSI,
                              @Named("macdLine") ICalculateJob calculateMACDLine,
                              @Named("macdSignal") ICalculateJob calculateMACDSignal,
                              @Named("rising") ICalculateJob calculateRising,
                              @Named("nextRising") ICalculateJob calculateNextRising) {
        this.calculateSMA = calculateSMA;
        this.calculateEMA = calculateEMA;
        this.calculateAverageVolume = calculateAverageVolume;
        this.calculateRSI = calculateRSI;
        this.calculateMACDLine = calculateMACDLine;
        this.calculateMACDSignal = calculateMACDSignal;
        this.calculateRising = calculateRising;
        this.calculateNextRising = calculateNextRising;
    }

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
