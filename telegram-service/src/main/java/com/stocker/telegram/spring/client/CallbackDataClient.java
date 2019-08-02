package com.stocker.telegram.spring.client;

import com.netflix.discovery.DiscoveryClient;
import com.stocker.telegram.spring.callback.AbstractCallback;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class CallbackDataClient extends AbstractClient {

    private static final String SERVICE = "stocker-data";

    public Mono<AddToWatchListCallback> getAddToWatchListCallback(String id) {
        log.info(String.format("getting callback with id %s", id));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("callback/%s/", id))
                .retrieve()
                .bodyToMono(AddToWatchListCallback.class);
    }

    public Mono<AbstractCallback> addCallback(AbstractCallback callback) {
        log.info(String.format("add new callback with id %s", callback.getId()));
        return this.getWebClient(SERVICE)
                .post()
                .uri("/addCallback/")
                .syncBody(callback).retrieve().bodyToMono(AbstractCallback.class);
    }
}