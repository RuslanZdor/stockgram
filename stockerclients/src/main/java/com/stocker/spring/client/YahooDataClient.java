package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class YahooDataClient extends AbstractClient {

    private static final String SERVICE = "YAHOO-SERVICE";

    public YahooDataClient(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    public Mono<Company> updateCompany(String symbol) {
        log.info(String.format("send stock for update %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("/manager/refresh/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }

    public boolean isAvailable() {
        return this.isAvailable(SERVICE);
    }
}