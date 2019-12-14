package com.stocker.data.spring.repo;

import com.stocker.data.bean.Callback;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallbackRepository extends ReactiveCrudRepository<Callback, String> {

}
