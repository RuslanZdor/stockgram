package com.stocker.telegram;

import com.stocker.telegram.spring.client.CallbackDataClient;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
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