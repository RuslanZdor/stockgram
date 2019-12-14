package com.stocker.spring;

import com.netflix.discovery.EurekaClient;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
public abstract class AbstractClient {

    @Autowired
    private EurekaClient discoveryClient;

    protected WebClient getWebClient(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("service name cannot be empty");
        }
        return WebClient.builder().baseUrl(discoveryClient.getApplication(serviceName).getInstances().get(0).getHomePageUrl()).build();
    }

    public EurekaClient getDiscoveryClient() {
        return discoveryClient;
    }
}