package com.stocker.data;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@ComponentScan(basePackages = "com.stocker")
@SpringBootApplication
@EnableEurekaClient
@EnableReactiveMongoRepositories("com.stocker")
public class StockDataConfiguration {

    @Value("${stocker.mongodb.url}")
    private String mongodbURL;

    public static void main(String[] args) {
        SpringApplication.run(StockDataConfiguration.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("Let's inspect the beans provided by Spring Boot:");
            Arrays.stream(ctx.getBeanDefinitionNames()).forEach(log::debug);
        };
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongodbURL);
    }

    private ReactiveMongoDatabaseFactory mongoDbFactory() {
        return new SimpleReactiveMongoDatabaseFactory(mongoClient(),
                "stocker");
    }
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }
}