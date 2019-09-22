package com.stocker.telegram;

import com.stocker.telegram.spring.StockTelegramComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.Arrays;

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

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
    public StockTelegramComponent stockTelegramComponent() {
        return new StockTelegramComponent();
    }
}
