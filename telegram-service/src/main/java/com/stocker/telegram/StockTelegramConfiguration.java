package com.stocker.telegram;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
@ComponentScan(basePackages = "com.stocker")
public class StockTelegramConfiguration {

    @Bean
    public StockTelegramComponent stockTelegramComponent() {
        return new StockTelegramComponent();
    }
}
