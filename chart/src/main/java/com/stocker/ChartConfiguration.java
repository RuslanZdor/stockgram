package com.stocker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Arrays;

@Slf4j
@Configuration
@ComponentScan(basePackages = "com.stocker")
@SpringBootApplication
@EnableEurekaClient
@EnableWebMvc
public class ChartConfiguration implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ChartConfiguration.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            log.debug("Let's inspect the beans provided by Spring Boot:");
            Arrays.stream(ctx.getBeanDefinitionNames()).forEach(log::debug);
        };
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        log.debug("configureViewResolvers !!!!");

        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/view/js/");
    }
}
