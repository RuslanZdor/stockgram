package com.stocker.telegram.spring.client;

import com.stocker.yahoo.data.StrategyResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
public class StrategyResultDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    public Flux<StrategyResult> getStrategyResult(String strategyName) {
        log.info(String.format("getting strategy result with name %s", strategyName));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("strategy/%s/result", strategyName))
                .retrieve()
                .bodyToFlux(StrategyResult.class);
    }
}