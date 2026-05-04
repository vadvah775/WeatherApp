package com.weatherApp.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "com.weatherApp.services",
        "com.weatherApp.repositories",
        "com.weatherApp.filters",
        "com.weatherApp.util"
})
@PropertySource("classpath:session.properties")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
