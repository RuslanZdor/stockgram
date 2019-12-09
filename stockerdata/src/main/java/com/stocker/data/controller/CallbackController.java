package com.stocker.data.controller;

import com.stocker.data.bean.Callback;
import com.stocker.data.spring.repo.CallbackRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * API controller for telegram callback
 */
@Log4j2
@RestController
class CallbackController {

    @Autowired
    private CallbackRepository repository;

    /**
     * Return @Callback object in json format if it available
     * @param id to search callback
     * @return Callback object
     * @throws IllegalArgumentException in case of blank id or empty results
     */
    @GetMapping("/callback/{id}/")
    public Mono<Callback> getCallback(@PathVariable("id") String id) throws IllegalArgumentException {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("Callback id cannot be empty");
        }
        log.info(String.format("search callback by id %s", id));
        return repository.findById(Mono.just(id)).switchIfEmpty(Mono.error(new IllegalArgumentException("There are no results for this callback id")));
    }

    @PostMapping(path = "/addCallback/")
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher<Callback> addCallback(@RequestBody Callback callback) {
        log.info(callback.toString());
        return repository.save(callback);
    }

}
