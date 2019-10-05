package com.stocker.data.controller;

import com.stocker.data.bean.User;
import com.stocker.data.spring.repo.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
class UserController {

    @Autowired
    private UserRepository repository;

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
