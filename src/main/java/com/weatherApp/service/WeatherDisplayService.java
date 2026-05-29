package com.weatherApp.service;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface WeatherDisplayService {
    List<WeatherDisplayDto> getWeatherForGuest();

    List<WeatherDisplayDto> getWeatherForUser(CurrentUserDto currentUserDto);
}
