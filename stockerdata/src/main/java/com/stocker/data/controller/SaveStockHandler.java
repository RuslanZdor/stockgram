package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Stock;
import com.stocker.data.dao.StockDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Save stock information to datastore
 */
@Slf4j
public class SaveStockHandler implements RequestHandler<Stock, String> {

    private final StockDAO stockDAO;

    public SaveStockHandler() {
        this(Guice.createInjector(new DAOModule()));
    }
    public SaveStockHandler(Injector injector) {
        stockDAO = injector.getInstance(StockDAO.class);
    }

    /**
     * Save stock information to datastore
     * @param stock object to save
     * @param context AWS context
     * @return status of save operation
     */
    @Override
    public String handleRequest(Stock stock, Context context) {
        stockDAO.save(stock);
        return "SUCCESS";
    }

}
