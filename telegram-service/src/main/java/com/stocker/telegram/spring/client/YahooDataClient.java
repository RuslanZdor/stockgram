package com.stocker.telegram.spring.client;

import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class YahooDataClient extends AbstractClient {

    private static final String SERVICE = "yahoo-service";
    public Mono<Company> updateCompany(String symbol) {
        log.info(String.format("send stock for update %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("/manager/refresh/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }
}