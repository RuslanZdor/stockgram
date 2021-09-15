package com.stocker.data.controller.market;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String symbol = "ALL";
        if (event.getQueryStringParameters() != null && event.getQueryStringParameters().containsKey("symbol")) {
            symbol = event.getQueryStringParameters().get("symbol");
        }
        log.info(String.format("Load data for market %s", symbol));
        return buildResponse(marketDayDAO.findAllData(symbol));
    }

    private APIGatewayProxyResponseEvent buildResponse(Object body) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            response.setBody(new ObjectMapper().writeValueAsString(body));
        } catch (JsonProcessingException e) {
            log.error("Cannot convert object to json", e);
        }
        response.setStatusCode(HttpStatus.SC_OK);
        response.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
        return response;
    }
}
