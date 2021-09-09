package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Stock;
import lombok.extern.slf4j.Slf4j;

/**
 * Update all daily fields for company object
 */
@Slf4j
public class DeleteDailyFieldsHandler implements RequestHandler<Stock, String> {

    private final DayDAO dayDAO;

    public DeleteDailyFieldsHandler() {
        this(DIFactory.init());
    }

    public DeleteDailyFieldsHandler(Injector injector) {
        dayDAO = injector.getInstance(DayDAO.class);
    }

    public String handleRequest(Stock stock, Context context) {
        log.info(String.format("Delete daily fields for company %s", stock.getSymbol()));
        Company company = new Company();
        company.setDays(dayDAO.findLastYearData(stock.getSymbol()));
        company.getDays()
                .forEach(dayDAO::delete);
        return "SUCCESS";
    }
}
