package com.example.practice2.controller;

import com.example.practice2.config.Practice2Properties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private final Practice2Properties properties;

    public  ConfigController(Practice2Properties properties) {
        this.properties = properties;
    }

    @GetMapping
    public Practice2Properties getConfig() {
        return properties;
    }
}