package com.weatherApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDisplayDto {
    private String cityName;
    private String country;
    private double temp;
    private double feelsLike;
    private String description;
    private String iconCode;
    private int humidity;
}
