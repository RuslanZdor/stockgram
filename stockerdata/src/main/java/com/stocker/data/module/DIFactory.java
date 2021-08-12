package com.stocker.data.module;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class DIFactory {

    public static Injector init() {
        return Guice.createInjector(new DAOModule(),
                new StockLogicModule());
    }

}
