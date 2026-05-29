package com.weatherApp.dto.openWeatherResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Main {
    private double temp;
    @JsonProperty("feels_like")
    private double feelsLike;
    private int humidity;
    private int pressure;
}
