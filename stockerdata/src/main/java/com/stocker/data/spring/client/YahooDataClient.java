package com.stocker.data.spring.client;

import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
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

    public boolean isAvailable() {
        return this.isAvailable(SERVICE);
    }
}