package com.stocker.telegram.spring.client;

import com.stocker.telegram.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class YahooDataClient {

    private final WebClient client;

    public YahooDataClient() {
        this.client = WebClient.builder().baseUrl("http://localhost:8082/").build();
    }

    public Mono<Company> updateCompany(String symbol) {
        log.info(String.format("send stock for update %s", symbol));
        return this.client
                .get()
                .uri(String.format("/manager/refresh/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }
}