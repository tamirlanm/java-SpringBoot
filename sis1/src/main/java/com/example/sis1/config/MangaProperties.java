package com.example.sis1.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.sis1")
public record MangaProperties(boolean seedEnabled) {}