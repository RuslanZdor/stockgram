package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import com.stocker.yahoo.data.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

    protected UserDataClient(@Qualifier("eurekaClient") EurekaClient eurekaClient) {
        super(eurekaClient);
    }

    public Mono<User> getUser(String telegramId) {
        log.info(String.format("getting user with telegramId %s", telegramId));
        return this.getWebClient(SERVICE)
                .get()
                .uri(String.format("user/%s/", telegramId))
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> addUser(User user) {
        log.info(String.format("add new user with telegramId %s", user.getTelegramId()));
        return this.getWebClient(SERVICE)
                .post()
                .uri("/addUser/")
                .syncBody(user).retrieve().bodyToMono(User.class);
    }
}