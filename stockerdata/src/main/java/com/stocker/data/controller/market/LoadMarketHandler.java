package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.market.Market;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Load market day information based on market symbol
 */
@Slf4j
public class LoadMarketHandler implements RequestHandler<Market, Market> {

    private final MarketDayDAO marketDayDAO;

    public LoadMarketHandler() {
        this(DIFactory.init());
    }

    public LoadMarketHandler(Injector injector) {
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
    }

    public Market handleRequest(Market market, Context context) {
        log.info(String.format("Load data for market %s", market));
        if (StringUtils.isBlank(market.getSymbol())) {
            market.setSymbol("ALL");
        }
        return Market.builder()
                .days(new ArrayList<>(marketDayDAO.findAllData(market.getSymbol())))
                .build();
    }
}
