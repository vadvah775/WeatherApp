package com.weatherApp.exceptions;

public class UserAlreadyExistsException extends AuthException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
