package com.stocker.data.controller.stock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.StockDAO;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Stock;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Lambda function to load all available stock symbols and pass them to next step
 */
@Slf4j
public class ScheduleStocksRequestHandler implements RequestHandler<Object, List<Stock>> {

    private final StockDAO stockDAO;

    public ScheduleStocksRequestHandler() {
        this(Guice.createInjector(new DAOModule()));
    }
    public ScheduleStocksRequestHandler(Injector injector) {
        stockDAO = injector.getInstance(StockDAO.class);
    }

    /**
     * Load all stocks from datastore and return them as json ready object
     * @param request empty object
     * @param context AWS context
     * @return list of all stocks
     */
    @Override
    public List<Stock> handleRequest(Object request, Context context) {
        List<Stock> stocks = stockDAO.getAllStocks();
        log.info(String.format("Found stocks for update %s", stocks));
        return stocks;
    }

}
