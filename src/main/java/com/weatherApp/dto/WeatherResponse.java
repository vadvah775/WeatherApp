package com.weatherApp.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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
