package com.stocker.data.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API controller for telegram callback
 */
@Slf4j
@RequiredArgsConstructor
class CallbackController {
//
//    private final CallbackRepository repository;
//
//    /**
//     * Return @Callback object in json format if it available
//     * @param id to search callback
//     * @return Callback object
//     * @throws IllegalArgumentException in case of blank id or empty results
//     */
//    @GetMapping("/callback/{id}/")
//    public Mono<Callback> getCallback(@PathVariable("id") String id) throws IllegalArgumentException {
//        if (StringUtils.isBlank(id)) {
//            throw new IllegalArgumentException("Callback id cannot be empty");
//        }
//        log.info(String.format("search callback by id %s", id));
//        return repository.findById(Mono.just(id)).switchIfEmpty(Mono.error(new IllegalArgumentException("There are no results for this callback id")));
//    }
//
//    @PostMapping(path = "/addCallback/")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Publisher<Callback> addCallback(@RequestBody Callback callback) {
//        log.info(callback.toString());
//        return repository.save(callback);
//    }

}
