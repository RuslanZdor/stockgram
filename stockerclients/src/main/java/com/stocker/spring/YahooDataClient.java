package com.stocker.spring;

import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class YahooDataClient extends AbstractClient {

    private static final String SERVICE = "YAHOO-SERVICE";
    public Mono<Company> updateCompany(String symbol) {
        log.info(String.format("send stock for update %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("/manager/refresh/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }
}