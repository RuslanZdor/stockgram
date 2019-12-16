package com.stocker.spring;

import com.stocker.yahoo.data.callback.AbstractCallback;
import com.stocker.yahoo.data.callback.AddToWatchListCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
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