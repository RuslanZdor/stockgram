package com.stocker.telegram.spring;

import com.stocker.telegram.data.Company;
import com.stocker.telegram.data.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class UserDataClient {

    private WebClient client;

    public UserDataClient() {
        this.client = WebClient.builder().baseUrl("http://localhost:8081/").build();
    }

    public Mono<User> getUser(String telegramId) {
        log.info(String.format("getting user with telegramid %s", telegramId));
        return this.client
                .get()
                .uri(String.format("user/%s/", telegramId))
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> addUser(User user) {
        log.info(String.format("add new user with telegramid %s", user.getTelegramId()));
        return this.client
                .post()
                .uri("/addUser/")
                .syncBody(user).retrieve().bodyToMono(User.class);
    }
}