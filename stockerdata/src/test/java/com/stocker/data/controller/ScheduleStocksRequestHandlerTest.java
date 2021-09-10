package com.stocker.data.controller;

import com.google.inject.Injector;
import com.stocker.yahoo.data.Stock;
import com.stocker.data.dao.StockDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScheduleStocksRequestHandlerTest {

    private ScheduleStocksRequestHandler handler;
    private StockDAO stockDAO;

    @Before
    public void init() {
        stockDAO = Mockito.mock(StockDAO.class);
        Injector injector = Mockito.mock(Injector.class);
        Mockito.when(injector.getInstance(StockDAO.class)).thenReturn(stockDAO);
        handler = new ScheduleStocksRequestHandler(injector);
    }

    @Test
    public void handleRequestVerifyStockList() {
        List<Stock> datasetList = Arrays.asList(new Stock(), new Stock());
        Mockito.when(stockDAO.getAllStocks()).thenReturn(datasetList);
        assertEquals(datasetList.size(), handler.handleRequest(null, null).size());
    }
}