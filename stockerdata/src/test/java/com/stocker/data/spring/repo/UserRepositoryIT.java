package com.stocker.data.spring.repo;

import com.stocker.data.StockDataConfigurationForTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockDataConfigurationForTest.class})
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findFirstByTelegramId() {
        assertNotNull(userRepository.findFirstByTelegramId(Mono.just("test_id")).single().block());
    }
}