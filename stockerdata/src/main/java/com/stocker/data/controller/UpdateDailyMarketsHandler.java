package com.stocker.data.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.data.job.market.CalculateAllMarketFields;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.Market;
import com.stocker.yahoo.data.MarketDay;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Update all daily fields for company object
 */
@Slf4j
public class UpdateDailyMarketsHandler implements RequestHandler<Market, String> {

    private final IMarketCalculateJob calculateAllFields;
    private final MarketDayDAO marketDayDAO;
    private final DayDAO dayDAO;

    public UpdateDailyMarketsHandler() {
        this(DIFactory.init());
    }

    public UpdateDailyMarketsHandler(Injector injector) {
        calculateAllFields = injector.getInstance(CalculateAllMarketFields.class);
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
        dayDAO = injector.getInstance(DayDAO.class);
    }

    public String handleRequest(Market market, Context context) {
        log.info(String.format("Update daily market fields for company %s", market.getSymbol()));
        List<Day> lastDays = new ArrayList<>();
        market.setDays(new ArrayList<>(marketDayDAO.findAllData(market.getSymbol())));
        market.getStocks()
                .forEach(stock -> lastDays.add(dayDAO.findLastStockDay(stock).orElse(new Day())));
        market.getDays().add(MarketDay.builder()
                .symbol(market.getSymbol())
                .lastUpdate(System.currentTimeMillis())
                .dateTimestamp(lastDays.get(0).getDateTimestamp())
                .build());
        calculateAllFields.calculate(market, lastDays);
        market.getDays().stream()
                .filter(day -> !day.isFinished())
                .forEach(day -> {
                    day.setFinished(true);
                    marketDayDAO.save(day);
                });
        return "SUCCESS";
    }
}
