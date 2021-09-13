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
 * Save company information to datastore
 */
@Slf4j
public class SaveStocksHandler implements RequestHandler<List<Stock>, String> {

    private final StockDAO stockDAO;

    public SaveStocksHandler() {
        this(Guice.createInjector(new DAOModule()));
    }

    public SaveStocksHandler(Injector injector) {
        stockDAO = injector.getInstance(StockDAO.class);
    }

    /**
     * Save stock information to datastore
     * @param stock object to save
     * @param context AWS context
     * @return status of save operation
     */
    @Override
    public String handleRequest(List<Stock> stock, Context context) {
        stock.forEach(stockDAO::save);
        return "SUCCESS";
    }

}
