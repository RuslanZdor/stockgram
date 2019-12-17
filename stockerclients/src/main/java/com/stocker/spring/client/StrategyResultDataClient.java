package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import com.stocker.yahoo.data.StrategyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class StrategyResultDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    public StrategyResultDataClient(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    public Flux<StrategyResult> getStrategyResult(String strategyName) {
        log.info(String.format("getting strategy result with name %s", strategyName));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("strategy/%s", strategyName))
                .retrieve()
                .bodyToFlux(StrategyResult.class);
    }
}