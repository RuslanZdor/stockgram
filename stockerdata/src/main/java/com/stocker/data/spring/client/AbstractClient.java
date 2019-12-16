package com.stocker.data.spring.client;

import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public abstract class AbstractClient {

    @Autowired
    private EurekaClient discoveryClient;

    WebClient getWebClient(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        return WebClient.builder().baseUrl(discoveryClient.getApplication(serviceName).getInstances().get(0).getHomePageUrl()).build();
    }

    boolean isAvailable(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        return discoveryClient.getApplication(serviceName) != null && discoveryClient.getApplication(serviceName).size() > 0;
    }

    public EurekaClient getDiscoveryClient() {
        return discoveryClient;
    }
}