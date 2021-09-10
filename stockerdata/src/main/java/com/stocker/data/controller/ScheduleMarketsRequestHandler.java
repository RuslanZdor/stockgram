package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.StockDAO;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Market;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Lambda function to load all available stock symbols and pass them to next step
 */
@Slf4j
public class ScheduleMarketsRequestHandler implements RequestHandler<Object, List<Market>> {

    private final StockDAO stockDAO;

    public ScheduleMarketsRequestHandler() {
        this(Guice.createInjector(new DAOModule()));
    }
    public ScheduleMarketsRequestHandler(Injector injector) {
        stockDAO = injector.getInstance(StockDAO.class);
    }

    /**
     * Load all markets and stocks for them from datastore and return them as json ready object
     * @param request empty object
     * @param context AWS context
     * @return list of all stocks
     */
    @Override
    public List<Market> handleRequest(Object request, Context context) {
        List<Market> markets = new ArrayList<>();
        markets.add(Market.builder()
                        .symbol("ALL")
                        .name("All Stocks")
                        .stocks(stockDAO.getAllStocks())
                        .build());
        return markets;
    }

}
