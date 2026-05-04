package com.weatherApp.exceptions;

public class OpenWeatherApiException extends RuntimeException {
    public OpenWeatherApiException(String message) {
        super(message);
    }

    public OpenWeatherApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
