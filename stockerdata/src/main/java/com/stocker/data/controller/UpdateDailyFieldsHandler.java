package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Company;
import com.stocker.data.job.CalculateAllFields;
import com.stocker.data.job.ICalculateJob;
import lombok.extern.slf4j.Slf4j;

/**
 * Update all daily fields for company object
 */
@Slf4j
public class UpdateDailyFieldsHandler implements RequestHandler<Company, Company> {

    private final ICalculateJob calculateAllFields;

    public UpdateDailyFieldsHandler() {
        this(DIFactory.init());
    }

    public UpdateDailyFieldsHandler(Injector injector) {
        calculateAllFields = injector.getInstance(CalculateAllFields.class);
    }

    public Company handleRequest(Company company, Context context) {
        log.info(String.format("Update daily fields for company %s", company.getSymbol()));
        allUpdates(company);
        return company;
    }

    private void allUpdates(Company company) {
        log.info(String.format("Calculate new values for %s", company.getName()));
        calculateAllFields.calculate(company);
    }
}
