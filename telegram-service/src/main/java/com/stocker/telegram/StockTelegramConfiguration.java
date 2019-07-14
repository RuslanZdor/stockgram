package com.stocker.telegram;

import com.stocker.telegram.spring.StockTelegramComponent;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.stocker")
public class StockTelegramConfiguration {

    @Bean
    public StockTelegramComponent stockTelegramComponent() {
        return new StockTelegramComponent();
    }
}
