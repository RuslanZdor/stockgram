package com.stocker.data.module;

import com.google.inject.AbstractModule;
import com.stocker.yahoo.spring.DownloadHistoricalData;

public class StockLogicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DownloadHistoricalData.class).toInstance(new DownloadHistoricalData());
    }
}
