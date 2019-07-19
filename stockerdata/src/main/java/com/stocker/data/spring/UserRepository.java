package com.stocker.data.spring;

import com.stocker.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findFirstByTelegramId(Mono<String> telegramId);
}
