package com.weatherApp.configs;

import com.weatherApp.services.OpenWeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestOpenWeatherConfig {

    @Bean
    public OpenWeatherService openWeatherService() {
        return new OpenWeatherService();
    }

}
