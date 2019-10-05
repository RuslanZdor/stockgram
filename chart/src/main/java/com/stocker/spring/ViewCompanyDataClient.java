package com.stocker.spring;

import com.stocker.yahoo.data.Company;
import com.stocker.yahoo.data.ViewCompany;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class ViewCompanyDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    public Mono<ViewCompany> getViewCompany(String symbol) {
        log.info(String.format("getting company with symbol %s", symbol));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("view/%s/", symbol))
                .retrieve()
                .bodyToMono(ViewCompany.class);
    }
}