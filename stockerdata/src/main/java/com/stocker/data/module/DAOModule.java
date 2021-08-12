package com.stocker.data.module;

import com.google.inject.AbstractModule;
import com.stocker.data.dao.CompanyDAO;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.dao.StockDAO;

public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StockDAO.class).toInstance(new StockDAO());
        bind(CompanyDAO.class).toInstance(new CompanyDAO());
        bind(DayDAO.class).toInstance(new DayDAO());
    }
}