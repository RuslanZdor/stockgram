package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import com.stocker.yahoo.data.ViewCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.core.publisher.Mono;

@Slf4j
public class ViewCompanyDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    public ViewCompanyDataClient(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    public Mono<ViewCompany> getViewCompany(String symbol) {
        log.info(String.format("getting company with symbol %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("view/%s/", symbol))
                .retrieve()
                .bodyToMono(ViewCompany.class);
    }
}