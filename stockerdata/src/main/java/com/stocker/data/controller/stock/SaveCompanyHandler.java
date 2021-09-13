package com.stocker.data.controller.stock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;

/**
 * Save company information to datastore
 */
@Slf4j
public class SaveCompanyHandler implements RequestHandler<Company, String> {

    private final DayDAO dayDAO;

    public SaveCompanyHandler() {
        this(Guice.createInjector(new DAOModule()));
    }

    public SaveCompanyHandler(Injector injector) {
        dayDAO = injector.getInstance(DayDAO.class);
    }

    /**
     * Save stock information to datastore
     * @param company object to save
     * @param context AWS context
     * @return status of save operation
     */
    @Override
    public String handleRequest(Company company, Context context) {
        company.getDays().forEach(dayDAO::save);
        return "SUCCESS";
    }

}
