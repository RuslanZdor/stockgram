package com.stocker.telegram;

import com.stocker.telegram.spring.StockTelegramComponent;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.Arrays;
@Log4j
@Configuration
@ComponentScan(basePackages = "com.stocker")
@SpringBootApplication
@EnableEurekaClient
public class StockTelegramConfiguration {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(StockTelegramConfiguration.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("Let's inspect the beans provided by Spring Boot:");
            Arrays.stream(ctx.getBeanDefinitionNames()).forEach(log::debug);
        };
    }

    @Bean
    public StockTelegramComponent stockTelegramComponent() {
        return new StockTelegramComponent();
    }
}
