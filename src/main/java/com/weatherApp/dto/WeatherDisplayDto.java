package com.weatherApp.dto;

import lombok.*;

@Getter
@Setter
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
    private long locationId;
}
