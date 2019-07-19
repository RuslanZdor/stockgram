package com.stocker.data.spring;

import com.stocker.data.Callback;
import com.stocker.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CallbackRepository extends ReactiveCrudRepository<Callback, String> {

}
