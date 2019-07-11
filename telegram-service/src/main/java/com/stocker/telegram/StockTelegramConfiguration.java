package com.stocker.telegram;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.stocker")
public class StockTelegramConfiguration {

    @Bean("telegramComponent")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StockTelegramComponent stockTelegramComponent() {
        return new StockTelegramComponent();
    }

    @Bean("telegramBot")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @DependsOn("telegramComponent")
    public StockTelegramBot stockTelegramBot() {
        return new StockTelegramBot();
    }

}
