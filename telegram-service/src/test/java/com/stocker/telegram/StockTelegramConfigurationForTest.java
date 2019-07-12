package com.stocker.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.stocker.telegram.spring")
public class StockTelegramConfigurationForTest {
}
