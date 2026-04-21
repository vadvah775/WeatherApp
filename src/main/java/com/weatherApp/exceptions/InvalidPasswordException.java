package com.weatherApp.exceptions;

public class InvalidPasswordException extends AuthException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
