package com.weatherApp.config;

import com.weatherApp.service.OpenWeatherService;
import com.weatherApp.service.impl.OpenWeatherServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestOpenWeatherConfig {

    @Bean
    public OpenWeatherService openWeatherService() {
        return new OpenWeatherServiceImpl();
    }

}
