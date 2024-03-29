package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.market.Market;
import com.stocker.yahoo.data.market.MarketUpdate;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lambda function to load all available markets and pass them to next step
 */
@Slf4j
public class ScheduleMarketsRequestHandler implements RequestHandler<Object, List<MarketUpdate>> {

    public ScheduleMarketsRequestHandler() {
        this(Guice.createInjector(new DAOModule()));
    }
    public ScheduleMarketsRequestHandler(Injector injector) {
    }

    /**
     * Load all markets and stocks for them from datastore and return them as json ready object
     * @param request empty object
     * @param context AWS context
     * @return list of all stocks
     */
    @Override
    public List<MarketUpdate> handleRequest(Object request, Context context) {
        List<MarketUpdate> markets = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            LocalDateTime now = LocalDateTime.now();
            now = now.minusDays(index).with(LocalTime.MIN);
            markets.add(MarketUpdate.builder()
                    .market(Market.builder()
                            .symbol("ALL")
                            .name("All Stocks")
                            .build())
                    .timestamp(Timestamp.valueOf(now).getTime())
                    .build());
        }
        return markets;
    }

}
