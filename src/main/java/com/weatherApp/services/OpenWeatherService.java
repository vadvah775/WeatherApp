package com.weatherApp.services;

import com.weatherApp.dto.WeatherResponse;
import com.weatherApp.exceptions.LocationNotFoundException;
import com.weatherApp.exceptions.OpenWeatherApiException;
import com.weatherApp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

@Service
public class OpenWeatherService {

    @Value("${openweather.api.key:key}")
    private String key;

    @Value("${openweather.api.apiUrl:url}")
    private String apiUrl;

    @Value("${openweather.api.lang:en}")
    private String lang;

    private LocationRepository locationRepository;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public WeatherResponse getWeatherByCoordinate(double lat, double lon) {
        String url = String.format(Locale.US, "%s?lat=%f&lon=%f&appid=%s&lang=%s", apiUrl, lat, lon, key, lang);

        return getWeatherResponse(url);
    }

    public WeatherResponse getWeatherByName(String name) {
        String url = String.format("%s?q=%s&appid=%s&lang=%s", apiUrl, name, key, lang);

        return getWeatherResponse(url);
    }

    private WeatherResponse getWeatherResponse(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), WeatherResponse.class);
            } else if (response.statusCode() == 404) {
                throw new LocationNotFoundException("City not found");
            } else {
                throw new OpenWeatherApiException("OpenWeather API error: HTTP "
                        + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new OpenWeatherApiException("OpenWeather API error", e);
        }
    }

    @Autowired(required = false)
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
