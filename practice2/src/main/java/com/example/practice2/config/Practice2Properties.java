package com.example.practice2.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "practice2")
public record Practice2Properties(
    @NotBlank
    String environment,

    @Min(1)
    @Max(1000)
    int maxItems,

    @NotNull
    Duration requestTimeout
){
}