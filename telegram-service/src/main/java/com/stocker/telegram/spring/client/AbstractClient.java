package com.stocker.telegram.spring.client;

import com.netflix.discovery.DiscoveryClient;
import com.stocker.telegram.spring.callback.AbstractCallback;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public abstract class AbstractClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    protected WebClient getWebClient(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        if (discoveryClient.getInstancesById(serviceName).isEmpty()) {
            throw new IllegalStateException(String.format("Service with name %s is not found", serviceName));
        }
        return WebClient.builder().baseUrl("http://localhost:8081/").build();
    }
}