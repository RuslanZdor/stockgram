package com.stocker.data.controller;

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
        handler = new ScheduleStocksRequestHandler(stockDAO);
    }

    @Test
    public void handleRequestVerifyStockList() {
        List<Stock> datasetList = Arrays.asList(new Stock(), new Stock());
        Mockito.when(stockDAO.getAllStocks()).thenReturn(datasetList);
        assertEquals(datasetList.size(), handler.handleRequest(null, null).getStocks().size());
    }
}