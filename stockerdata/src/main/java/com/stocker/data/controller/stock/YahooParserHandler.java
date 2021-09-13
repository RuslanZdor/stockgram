package com.stocker.data.controller.stock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Stock;
import com.stocker.yahoo.exception.NoDayException;
import com.stocker.yahoo.spring.DownloadHistoricalData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Main Controller for Yahoo parser service
 */
@Slf4j
@AllArgsConstructor
public class YahooParserHandler implements RequestHandler<Stock, Stock> {

    private final DayDAO dayDAO;
    private final DownloadHistoricalData downloadHistoricalData;

    public YahooParserHandler() {
        this(DIFactory.init());
    }

    public YahooParserHandler(Injector injector) {
        downloadHistoricalData = injector.getInstance(DownloadHistoricalData.class);
        dayDAO = injector.getInstance(DayDAO.class);
    }

    public Stock handleRequest(Stock stock, Context context) {
        log.info(String.format("Update all data for company %s", stock.getSymbol()));
        Company company;
        try {
            Optional<Day> day = dayDAO.findLastStockDay(stock);
            if (day.isPresent()) {
                company = downloadHistoricalData.download(stock, day.get());
            } else {
                company = downloadHistoricalData.download(stock);
            }
            company.getDays().forEach(dayDAO::save);
        } catch (NoDayException e) {
            log.error("Company hasn't historical information", e);
        }
        return stock;
    }
}
