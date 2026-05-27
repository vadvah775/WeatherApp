package com.weatherApp.exception;

public class LocationNotFoundException extends OpenWeatherApiException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}
