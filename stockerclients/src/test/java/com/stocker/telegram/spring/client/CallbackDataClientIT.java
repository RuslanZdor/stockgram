package com.stocker.telegram.spring.client;

import com.stocker.spring.CallbackDataClient;
import com.stocker.telegram.spring.StockConfigurationForTest;
import com.stocker.yahoo.data.callback.AddToWatchListCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockConfigurationForTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CallbackDataClientIT {

    @Autowired
    private CallbackDataClient client;
    @Test
    public void getCompany() {
        Mono<AddToWatchListCallback> testCompany = client.getAddToWatchListCallback("10648717-84d3-4921-8ba0-9099d416fd86");

        StepVerifier.create(testCompany)
                .expectNext(new AddToWatchListCallback("AAPL", "379767613"))
                .verifyComplete();
    }
}