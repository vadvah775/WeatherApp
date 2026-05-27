package com.weatherApp.service;

import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface WeatherDisplayService {
    List<WeatherDisplayDto> getWeatherForGuest();

    List<WeatherDisplayDto> getWeatherForUser(User user);

    @Autowired
    void setOpenWeatherService(OpenWeatherService openWeatherService);

    @Autowired
    void setLocationService(LocationService locationService);
}
