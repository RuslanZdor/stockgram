package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.DayDAO;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.dao.StockDAO;
import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.data.job.market.CalculateAllMarketDailyFields;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;
import com.stocker.yahoo.data.market.MarketDay;
import com.stocker.yahoo.data.market.MarketUpdate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Update all daily fields for market object
 */
@Slf4j
public class UpdateDailyMarketsHandler implements RequestHandler<MarketUpdate, String> {

    private final IMarketCalculateJob calculateAllFields;
    private final MarketDayDAO marketDayDAO;
    private final DayDAO dayDAO;
    private final StockDAO stockDAO;

    public UpdateDailyMarketsHandler() {
        this(DIFactory.init());
    }

    public UpdateDailyMarketsHandler(Injector injector) {
        calculateAllFields = injector.getInstance(CalculateAllMarketDailyFields.class);
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
        dayDAO = injector.getInstance(DayDAO.class);
        stockDAO = injector.getInstance(StockDAO.class);
    }

    public String handleRequest(MarketUpdate marketUpdate, Context context) {
        Market market = marketUpdate.getMarket();
        log.info(String.format("Update daily market fields for company %s", market.getSymbol()));
        List<Day> lastDays = new ArrayList<>();
        market.setDays(new ArrayList<>(marketDayDAO.findAllData(market.getSymbol())));
        market.setStocks(stockDAO.getAllStocks());
        market.getStocks()
                .forEach(stock -> dayDAO.findStockDay(stock, marketUpdate.getTimestamp()).ifPresent(lastDays::add));
        if (!lastDays.isEmpty()) {
            market.getDays().add(MarketDay.builder()
                    .symbol(market.getSymbol())
                    .lastUpdate(System.currentTimeMillis())
                    .dateTimestamp(marketUpdate.getTimestamp())
                    .build());
            calculateAllFields.calculate(market, lastDays);
            market.getDays().stream()
                    .filter(day -> !day.isFinished())
                    .forEach(marketDayDAO::save);
        }
        return "SUCCESS";
    }
}
