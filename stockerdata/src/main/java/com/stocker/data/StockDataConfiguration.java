package com.stocker.data;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.net.UnknownHostException;
import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "com.stocker")
@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveMongoRepositories("com.stocker")
public class StockDataConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(StockDataConfiguration.class, args);
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
    public MongoClient mongoClient() throws UnknownHostException {
        return MongoClients.create("mongodb://localhost");
    }
    public ReactiveMongoDatabaseFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleReactiveMongoDatabaseFactory(mongoClient(),
                "stocker");
    }
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() throws UnknownHostException {
        return new ReactiveMongoTemplate(mongoDbFactory());
    }
}