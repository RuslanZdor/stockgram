package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import com.stocker.yahoo.data.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * React cliect for COmpany data API
 */
@Slf4j
@Component
public class CompanyDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    public CompanyDataClient(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    /**
     * Return All information about  company with historical proce data
     * @param symbol to serch company
     * @return React Wrapper for Company object
     */
    public Mono<Company> getCompany(String symbol) {
        log.info(String.format("getting company with symbol %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("company/%s/", symbol))
                .retrieve()
                .bodyToMono(Company.class);
    }
}