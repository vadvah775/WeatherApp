package com.weatherApp.service.impl;

import com.weatherApp.dto.openWeatherResponse.GeocodingResponse;
import com.weatherApp.dto.openWeatherResponse.WeatherResponse;
import com.weatherApp.exception.LocationNotFoundException;
import com.weatherApp.exception.OpenWeatherApiException;
import com.weatherApp.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Locale;

@Service
public class OpenWeatherServiceImpl implements OpenWeatherService {

    @Value("${openweather.api.key:key}")
    private String key;

    @Value("${openweather.api.apiUrl:url}")
    private String apiUrl;

    @Value("${openweather.api.geoUrl:url}")
    private String geoUrl;

    @Value("${openweather.api.lang:en}")
    private String lang;

    private HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public WeatherResponse getWeatherByCoordinate(double lat, double lon) {
        String url = String.format(Locale.US, "%s?lat=%f&lon=%f&appid=%s&lang=%s", apiUrl, lat, lon, key, lang);

        return getWeatherResponse(url);
    }

    @Override
    public WeatherResponse getWeatherByName(String name) {
        String url = String.format("%s?q=%s&appid=%s&lang=%s", apiUrl, name, key, lang);

        return getWeatherResponse(url);
    }

    @Override
    public List<GeocodingResponse> searchLocations(String query) {
        String url = String.format(Locale.US, "%s?q=%s&limit=5&appid=%s&lang=%s", geoUrl, query, key, lang);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<GeocodingResponse>>() {
                });
            } else if (response.statusCode() == 404) {
                throw new LocationNotFoundException("Location not found");
            } else {
                throw new OpenWeatherApiException("OpenWeather API error: HTTP "
                        + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new OpenWeatherApiException("Failed to search locations", e);
        }
    }

    private WeatherResponse getWeatherResponse(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response == null) {
                throw new OpenWeatherApiException("api connection error");
            }

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
}
