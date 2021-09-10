package com.stocker.data.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.stocker.data.job.*;
import com.stocker.data.job.market.CalculateBreadthThrust;
import com.stocker.data.job.stock.*;
import com.stocker.yahoo.spring.DownloadHistoricalData;

public class StockLogicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DownloadHistoricalData.class).toInstance(new DownloadHistoricalData());
        bind(ICalculateJob.class).annotatedWith(Names.named("ema")).to(CalculateEMA.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("averageVolume")).to(CalculateAverageVolume.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("macdLine")).to(CalculateMACDLine.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("macdSignal")).to(CalculateMACDSignal.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("nextRising")).to(CalculateNextRising.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("rising")).to(CalculateRising.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("rsi")).to(CalculateRSI.class);
        bind(ICalculateJob.class).annotatedWith(Names.named("sma")).to(CalculateSMA.class);
        bind(IMarketCalculateJob.class).annotatedWith(Names.named("breadthThrust")).to(CalculateBreadthThrust.class);
    }
}

