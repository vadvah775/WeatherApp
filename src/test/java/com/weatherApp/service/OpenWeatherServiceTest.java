package com.weatherApp.service;

import com.weatherApp.BaseOpenWeatherTest;
import com.weatherApp.dto.WeatherResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;


public class OpenWeatherServiceTest extends BaseOpenWeatherTest {

    private OpenWeatherService openWeatherService;


    private final String jsonResponse = """
            {
                "coord": {"lat": 55.75, "lon": 37.62},
                "name": "Moscow",
                "main": {"temp": 20.5, "feels_like": 19.0, "humidity": 65},
                "weather": [{"main": "Clouds", "description": "few clouds", "icon": "01d"}]
            }
            """;

    @Test
    public void getWeatherByCoordinates_Success() {
        double lat = 55.75;
        double lon = 37.62;

        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
                .withQueryParam("lat", equalTo(String.format(Locale.US, "%f", lat)))
                .withQueryParam("lon", equalTo(String.format(Locale.US, "%f", lon)))
                .withQueryParam("appid", equalTo("testKey"))
                .withQueryParam("lang", equalTo("en"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));


        WeatherResponse response = openWeatherService.getWeatherByCoordinate(lat, lon);

        assertNotNull(response);
        assertEquals("Moscow", response.getName());
        assertEquals(20.5, response.getMain().getTemp(), 0.01);
        assertEquals("Clouds", response.getWeather().get(0).getMain());
    }

    @Test
    public void getWeatherByName_Success() {
        String name = "Moscow";

        stubFor(get(urlPathEqualTo("/data/2.5/weather"))
                .withQueryParam("q", equalTo(name))
                .withQueryParam("appid", equalTo("testKey"))
                .withQueryParam("lang", equalTo("en"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonResponse)));

        WeatherResponse response = openWeatherService.getWeatherByName(name);

        assertNotNull(response);
        assertEquals("Moscow", response.getName());
        assertEquals(20.5, response.getMain().getTemp(), 0.01);
        assertEquals("Clouds", response.getWeather().get(0).getMain());
    }

    @Autowired
    public void setOpenWeatherService(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }
}
