package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Injector;
import com.stocker.data.dao.MarketDayDAO;
import com.stocker.data.module.DIFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.util.Collections;

/**
 * Load market day information based on market symbol
 */
@Slf4j
public class LoadMarketHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final MarketDayDAO marketDayDAO;

    public LoadMarketHandler() {
        this(DIFactory.init());
    }

    public LoadMarketHandler(Injector injector) {
        marketDayDAO = injector.getInstance(MarketDayDAO.class);
    }

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String symbol = event.getQueryStringParameters().getOrDefault("symbol", "ALL");
        log.info(String.format("Load data for market %s", symbol));
        return buildResponse(marketDayDAO.findAllData(symbol).toString());
    }

    private APIGatewayProxyResponseEvent buildResponse(String body) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody(body);
        response.setStatusCode(HttpStatus.SC_OK);
        response.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
        return response;
    }
}
