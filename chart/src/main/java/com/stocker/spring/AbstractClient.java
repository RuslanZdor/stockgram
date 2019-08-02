package com.stocker.spring;

import com.netflix.discovery.DiscoveryClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
        return WebClient.builder().baseUrl(discoveryClient.getInstancesById(serviceName).get(0).getHostName()).build();
    }
}