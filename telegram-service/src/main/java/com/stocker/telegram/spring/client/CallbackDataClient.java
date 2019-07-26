package com.stocker.telegram.spring.client;

import com.stocker.telegram.spring.callback.AbstractCallback;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class CallbackDataClient {

    private final WebClient client;

    public CallbackDataClient() {
        this.client = WebClient.builder().baseUrl("http://localhost:8081/").build();
    }

    public Mono<AddToWatchListCallback> getAddToWatchListCallback(String id) {
        log.info(String.format("getting callback with id %s", id));
        return this.client
                .get()
                .uri(String.format("callback/%s/", id))
                .retrieve()
                .bodyToMono(AddToWatchListCallback.class);
    }

    public Mono<AbstractCallback> addCallback(AbstractCallback callback) {
        log.info(String.format("add new callback with id %s", callback.getId()));
        return this.client
                .post()
                .uri("/addCallback/")
                .syncBody(callback).retrieve().bodyToMono(AbstractCallback.class);
    }
}