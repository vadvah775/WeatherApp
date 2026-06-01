package com.weatherApp.service.impl;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.dto.openWeatherResponse.WeatherResponse;
import com.weatherApp.entity.Location;
import com.weatherApp.entity.User;
import com.weatherApp.exception.UserNotFoundException;
import com.weatherApp.repository.UserRepository;
import com.weatherApp.service.LocationService;
import com.weatherApp.service.OpenWeatherService;
import com.weatherApp.service.WeatherDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherDisplayServiceImpl implements WeatherDisplayService {

    private OpenWeatherService openWeatherService;
    private LocationService locationService;

    private UserRepository userRepository;

    @Override
    @Transactional
    public List<WeatherDisplayDto> getWeatherForGuest() {
        List<WeatherDisplayDto> result = new ArrayList<>();

        try {
            System.out.println("start accessing to api");
            WeatherResponse response = openWeatherService.getWeatherByName("Kazan");
            WeatherDisplayDto dto = mapToWeatherDisplayDto(response, 0L);
            result.add(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get weather for default city");
        }
        return result;
    }

    @Override
    @Transactional
    public List<WeatherDisplayDto> getWeatherForUser(CurrentUserDto currentUserDto) {
        Optional<User> optUser = userRepository.findByLogin(currentUserDto.getLogin());
        if(optUser.isEmpty()){
            throw new UserNotFoundException("User with this username was not found");
        }
        List<Location> locations = locationService.getLocationByUser(optUser.get());
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

    private WeatherDisplayDto mapToWeatherDisplayDto(WeatherResponse response, long locationId) {
        if (response == null || response.getMain() == null || response.getWeather().isEmpty()) {
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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
