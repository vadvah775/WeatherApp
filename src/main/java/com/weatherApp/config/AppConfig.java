package com.weatherApp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
        "com.weatherApp.service",
        "com.weatherApp.repository",
        "com.weatherApp.filter",
        "com.weatherApp.util"
})
@PropertySource("classpath:session.properties")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
