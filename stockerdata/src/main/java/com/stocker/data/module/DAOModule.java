package com.stocker.data.module;

import com.google.inject.AbstractModule;
import com.stocker.data.dao.StockDAO;

public class DAOModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StockDAO.class).to(StockDAO.class);
    }
}