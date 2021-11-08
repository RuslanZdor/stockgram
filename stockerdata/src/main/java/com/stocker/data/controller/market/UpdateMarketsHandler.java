package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.inject.Injector;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.job.IMarketCalculateJob;
import com.stocker.data.job.market.CalculateAllMarketFields;
import com.stocker.data.module.DIFactory;
import com.stocker.yahoo.data.Day;
import com.stocker.yahoo.data.market.Market;
import com.stocker.yahoo.data.market.MarketUpdate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Update all fields for market object
 */
@Slf4j
public class UpdateMarketsHandler implements RequestHandler<MarketUpdate, String> {

    private final IMarketCalculateJob calculateAllFields;
    private final MarketDayDAO marketDayDAO;

    public UpdateMarketsHandler() {
        this(DIFactory.init());
    }

    public UpdateMarketsHandler(Injector injector) {
        calculateAllFields = injector.getInstance(CalculateAllMarketFields.class);
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
    }

    public String handleRequest(MarketUpdate marketUpdate, Context context) {
        Market market = marketUpdate.getMarket();
        log.info(String.format("Update market fields for company %s", market.getSymbol()));
        List<Day> lastDays = new ArrayList<>();
        market.setDays(new ArrayList<>(marketDayDAO.findAllData(market.getSymbol())));
        calculateAllFields.calculate(market, lastDays);
        market.getDays()
                .stream().filter(marketDay -> !marketDay.isFinished())
                .forEach(day -> {
                    log.debug(String.format("Update %s market day %s", market.getSymbol(), day.getDateTimestamp()));
                    day.setFinished(true);
                    marketDayDAO.save(day);
                });
        return "SUCCESS";
    }
}
