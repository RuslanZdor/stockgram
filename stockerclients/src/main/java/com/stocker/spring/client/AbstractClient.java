package com.stocker.spring.client;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractClient {

    private final EurekaClient eurekaClient;

    WebClient getWebClient(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        return WebClient.builder().baseUrl(eurekaClient.getApplication(serviceName).getInstances().get(0).getHomePageUrl()).build();
    }

    boolean isAvailable(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        return eurekaClient.getApplication(serviceName) != null && eurekaClient.getApplication(serviceName).size() > 0;
    }

    public EurekaClient getDiscoveryClient() {
        return eurekaClient;
    }
}