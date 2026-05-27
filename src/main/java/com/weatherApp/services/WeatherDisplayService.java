package com.weatherApp.services;

import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.dto.WeatherResponse;
import com.weatherApp.models.Location;
import com.weatherApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherDisplayService {

    private OpenWeatherService openWeatherService;
    private LocationService locationService;

    public List<WeatherDisplayDto> getWeatherForGuest() {
        List<WeatherDisplayDto> result = new ArrayList<>();

        try {
            System.out.println("start accessing to api");
            WeatherResponse response = openWeatherService.getWeatherByName("Kazan");
            WeatherDisplayDto dto = mapToWeatherDisplayDto(response, 0L);
            System.out.println(dto);
            result.add(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get weather for default city");
        }
        return result;
    }

    public List<WeatherDisplayDto> getWeatherForUser(User user){
        List<Location> locations = locationService.getLocationByUser(user);
        List<WeatherDisplayDto> result = new ArrayList<>();
        for (Location loc : locations) {
            try {
                WeatherResponse response = openWeatherService.getWeatherByCoordinate(
                        loc.getLatitude().doubleValue(),
                        loc.getLongitude().doubleValue()
                );
                if (response != null) {
                    WeatherDisplayDto dto = mapToWeatherDisplayDto(response, loc.getId());
                    result.add(dto);
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch weather for location " + loc.getName() + ": " + e.getMessage());
            }
        }
        return result;
    }

    private WeatherDisplayDto mapToWeatherDisplayDto(WeatherResponse response, long locationId){
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
                response.getMain().getHumidity(),
                locationId
        );
    }


    @Autowired
    public void setOpenWeatherService(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }
}
