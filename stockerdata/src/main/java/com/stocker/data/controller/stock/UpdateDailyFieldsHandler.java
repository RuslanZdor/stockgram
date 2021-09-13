package com.stocker.data.controller.stock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.job.stock.CalculateAllFields;
import com.stocker.data.job.ICalculateJob;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Stock;
import lombok.extern.slf4j.Slf4j;

/**
 * Update all daily fields for company object
 */
@Slf4j
public class UpdateDailyFieldsHandler implements RequestHandler<Stock, String> {

    private final ICalculateJob calculateAllFields;
    private final DayDAO dayDAO;

    public UpdateDailyFieldsHandler() {
        this(DIFactory.init());
    }

    public UpdateDailyFieldsHandler(Injector injector) {
        calculateAllFields = injector.getInstance(CalculateAllFields.class);
        dayDAO = injector.getInstance(DayDAO.class);
    }

    public String handleRequest(Stock stock, Context context) {
        log.info(String.format("Update daily fields for company %s", stock.getSymbol()));
        Company company = new Company();
        company.setDays(dayDAO.findLastYearData(stock.getSymbol()));
        allUpdates(company);
        company.getDays().stream()
                .filter(day -> !day.isFinished())
                .forEach(day -> {
            day.setFinished(true);
            dayDAO.save(day);
        });
        return "SUCCESS";
    }

    private void allUpdates(Company company) {
        log.info(String.format("Calculate new values for %s", company.getName()));
        calculateAllFields.calculate(company);
    }
}
