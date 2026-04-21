package com.weatherApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.weatherApp.services",
        "com.weatherApp.repositories",
        "com.weatherApp.filters",
        "com.weatherApp.util"
})
public class AppConfig {
}
