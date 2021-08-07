package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stocker.data.bean.StockList;
import com.stocker.data.dao.StockDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Lambda function to load all available stock symbols and pass them to next step
 */
@Slf4j
public class ScheduleStocksRequestHandler implements RequestHandler<Object, StockList> {

    private StockDAO stockDAO;

    public ScheduleStocksRequestHandler() {
        stockDAO = new StockDAO();
    }

    public ScheduleStocksRequestHandler(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    /**
     * Load all stocks from datastore and return them as json ready object
     * @param request empty object
     * @param context AWS context
     * @return list of all stocks
     */
    @Override
    public StockList handleRequest(Object request, Context context) {
        StockList stocks = new StockList();
        stocks.setStocks(stockDAO.getAllStocks());
        return stocks;
    }

}
