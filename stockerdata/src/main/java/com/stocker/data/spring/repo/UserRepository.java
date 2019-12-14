package com.stocker.data.spring.repo;

import com.stocker.data.bean.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findFirstByTelegramId(Mono<String> telegramId);
}
