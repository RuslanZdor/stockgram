package com.stocker.telegram.spring;

import com.stocker.telegram.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class CompanyDataClient {

    private WebClient client;

    public CompanyDataClient() {
        this.client = WebClient.builder().baseUrl("http://localhost:8081/").build();
    }

    public Mono<Company> getCompany(String symbol) {
        log.info(String.format("getting company with symbol %s", symbol));
        return this.client
                .get()
                .uri(String.format("company/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }
}