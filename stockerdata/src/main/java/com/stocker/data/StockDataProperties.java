package com.stocker.data;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration to read application.yml file
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class StockDataProperties {

    @Getter
    @Value("${stocker.strategy.dividendAristocrats}")
    private String dividendAristocrats;
}