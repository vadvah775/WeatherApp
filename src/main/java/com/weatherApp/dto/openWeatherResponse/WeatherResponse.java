package com.weatherApp.dto.openWeatherResponse;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {
    private Coord coord;
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private Sys sys;
    private String name;
    private int code;
}
