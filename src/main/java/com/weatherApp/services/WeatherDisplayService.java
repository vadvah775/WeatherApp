package com.weatherApp.services;

import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.dto.WeatherResponse;
import com.weatherApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherDisplayService {

    private OpenWeatherService openWeatherService;

    public List<WeatherDisplayDto> getWeatherForGuest() {
        List<WeatherDisplayDto> result = new ArrayList<>();

        try {
            WeatherResponse response = openWeatherService.getWeatherByName("Moscow");
            WeatherDisplayDto dto = mapToWeatherDisplayDto(response);
            System.out.println(dto);
            result.add(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get weather for default city");
        }
        return result;
    }

    public List<WeatherDisplayDto> getWeatherForUser(User user){
        return new ArrayList<>();
    }

    private WeatherDisplayDto mapToWeatherDisplayDto(WeatherResponse response){
        if (response == null || response.getMain() == null || response.getWeather().isEmpty()){
            throw new RuntimeException("Invalid weather response");
        }
        var weather = response.getWeather().get(0);
        double tempC = response.getMain().getTemp() - 273.15;
        double feelsLikeC = response.getMain().getFeelsLike() - 273.15;

        return new WeatherDisplayDto(
                response.getName(),
                response.getSys().getCountry(),
                tempC,
                feelsLikeC,
                weather.getDescription(),
                weather.getIcon(),
                response.getMain().getHumidity()
        );
    }


    @Autowired
    public void setOpenWeatherService(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }
}
