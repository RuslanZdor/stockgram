package com.stocker.data.module;

import com.google.inject.AbstractModule;
import com.stocker.data.dao.StockDAO;
import com.stocker.yahoo.data.Company;

public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StockDAO.class).toInstance(new StockDAO());
        bind(Company.class).toInstance(new Company());
    }
}