package com.stocker.data.controller;

import com.stocker.data.bean.Callback;
import com.stocker.data.spring.repo.CallbackRepository;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
class CallbackController {

    @Autowired
    private CallbackRepository repository;

    @GetMapping("/callback/{id}/")
    public Publisher<Callback> getCallback(@PathVariable("id") String id) {
        log.info(String.format("search callback by id %s", id));
        return repository.findById(Mono.just(id));
    }

    @PostMapping(path = "/addCallback/")
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher<Callback> addCallback(@RequestBody Callback callback) {
        log.info(callback.toString());
        return repository.save(callback);
    }

}
