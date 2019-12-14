package com.stocker.spring;

import com.stocker.telegram.data.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class UserDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-DATA";

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