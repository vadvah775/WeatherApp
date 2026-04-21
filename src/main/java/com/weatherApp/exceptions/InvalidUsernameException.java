package com.weatherApp.exceptions;

public class InvalidUsernameException extends AuthException{
    public InvalidUsernameException(String message) {
        super(message);
    }
}
