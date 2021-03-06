package com.stocker.data.controller;

import com.stocker.data.bean.User;
import com.stocker.data.spring.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Rest API for User Objects
 */
@Slf4j
@RestController
@RequiredArgsConstructor
class UserController {

    private final UserRepository repository;

    @GetMapping("/user/{telegramId}/")
    public Publisher<User> getUser(@PathVariable("telegramId") String telegramId) {
        log.info(String.format("search user by telegram id %s", telegramId));
        return repository.findFirstByTelegramId(Mono.just(telegramId));
    }

    @PostMapping(path = "/addUser/")
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher<User> addUser(@RequestBody User user) {
        log.info(user.toString());
        return repository.save(user);
    }

}
