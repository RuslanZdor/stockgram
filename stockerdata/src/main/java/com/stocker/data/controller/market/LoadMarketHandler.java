package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.market.Market;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Load market day information based on market symbol
 */
@Slf4j
public class LoadMarketHandler implements RequestHandler<String, Market> {

    private final MarketDayDAO marketDayDAO;

    public LoadMarketHandler() {
        this(DIFactory.init());
    }

    public LoadMarketHandler(Injector injector) {
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
    }

    public Market handleRequest(String marketSymbol, Context context) {
        log.info(String.format("Load data for market %s", marketSymbol));
        if (marketSymbol.isEmpty()) {
            marketSymbol = "ALL";
        }
        return Market.builder()
                .days(new ArrayList<>(marketDayDAO.findAllData(marketSymbol)))
                .build();
    }
}
