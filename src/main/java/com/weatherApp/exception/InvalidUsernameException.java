package com.weatherApp.exception;

public class InvalidUsernameException extends AuthException{
    public InvalidUsernameException(String message) {
        super(message);
    }
}
