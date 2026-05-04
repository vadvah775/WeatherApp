package com.weatherApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class Main {
    private double temp;
    @JsonProperty("feels_like")
    private double feelsLike;
    private int humidity;
    private int pressure;
}
