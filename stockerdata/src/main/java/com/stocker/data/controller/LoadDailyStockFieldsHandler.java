package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Stock;
import lombok.extern.slf4j.Slf4j;

import java.util.TreeSet;

/**
 * Load stock history data from datastore
 */
@Slf4j
public class LoadDailyStockFieldsHandler implements RequestHandler<Stock, Company> {

    private final DayDAO dayDAO;

    public LoadDailyStockFieldsHandler() {
        this(Guice.createInjector(new DAOModule()));
    }

    public LoadDailyStockFieldsHandler(Injector injector) {
        dayDAO = injector.getInstance(DayDAO.class);
    }

    /**
     * Load last year of historical data to update
     * @param stock to search
     * @param context AWS context
     * @return status of save operation
     */
    @Override
    public Company handleRequest(Stock stock, Context context) {
        Company company = new Company();
        company.setDays(new TreeSet<>(dayDAO.findLastYearData(stock.getSymbol())));
        return company;
    }

}
