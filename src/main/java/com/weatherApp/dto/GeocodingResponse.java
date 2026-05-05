package com.weatherApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeocodingResponse {
    private String name;
    private String country;
    private String state;
    private double lat;
    private double lon;
}
