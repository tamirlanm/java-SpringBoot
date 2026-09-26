package com.example.practice1.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalBeanConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "practice1",
            name = "environment",
            havingValue = "development"
    )
    public CommandLineRunner developmentBean() {
        return args ->
                System.out.println("Development conditional bean is enabled");
    }
}