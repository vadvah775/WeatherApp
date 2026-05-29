package com.weatherApp.dto.openWeatherResponse;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeocodingResponse {
    private String name;
    private String country;
    private String state;
    private double lat;
    private double lon;
}
