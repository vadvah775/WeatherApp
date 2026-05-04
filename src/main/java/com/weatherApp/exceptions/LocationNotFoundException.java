package com.weatherApp.exceptions;

public class LocationNotFoundException extends OpenWeatherApiException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}
