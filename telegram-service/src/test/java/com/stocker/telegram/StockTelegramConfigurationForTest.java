package com.stocker.telegram;

import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

@Configuration
@ComponentScan(basePackages = "com.stocker.telegram.spring")
public class StockTelegramConfigurationForTest {

}
