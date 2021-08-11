package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.stocker.data.dao.CompanyDAO;
import com.stocker.data.module.DAOModule;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;

/**
 * Save stock information to datastore
 */
@Slf4j
public class SaveCompanyHandler implements RequestHandler<Company, String> {

    private final CompanyDAO companyDAO;

    public SaveCompanyHandler() {
        this(Guice.createInjector(new DAOModule()));
    }
    public SaveCompanyHandler(Injector injector) {
        companyDAO = injector.getInstance(CompanyDAO.class);
    }

    /**
     * Save stock information to datastore
     * @param company object to save
     * @param context AWS context
     * @return status of save operation
     */
    @Override
    public String handleRequest(Company company, Context context) {
        companyDAO.save(company);
        return "SUCCESS";
    }

}
