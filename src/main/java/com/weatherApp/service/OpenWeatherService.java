package com.weatherApp.service;

import com.weatherApp.dto.openWeatherResponse.GeocodingResponse;
import com.weatherApp.dto.openWeatherResponse.WeatherResponse;

import java.util.List;

public interface OpenWeatherService {
    WeatherResponse getWeatherByCoordinate(double lat, double lon);

    WeatherResponse getWeatherByName(String name);

    List<GeocodingResponse> searchLocations(String query);
}
