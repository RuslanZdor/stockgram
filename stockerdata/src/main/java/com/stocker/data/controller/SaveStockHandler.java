package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stocker.yahoo.data.Stock;
import com.stocker.data.dao.StockDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Save stock information to datastore
 */
@Slf4j
public class SaveStockHandler implements RequestHandler<Stock, String> {

    private StockDAO stockDAO;

    public SaveStockHandler() {
        stockDAO = new StockDAO();
    }

    public SaveStockHandler(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
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
