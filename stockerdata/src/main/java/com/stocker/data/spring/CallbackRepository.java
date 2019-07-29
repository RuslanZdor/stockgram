package com.stocker.data.spring;

import com.stocker.data.Callback;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallbackRepository extends ReactiveCrudRepository<Callback, String> {

}
