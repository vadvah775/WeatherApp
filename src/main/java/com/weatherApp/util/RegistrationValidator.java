package com.weatherApp.util;

import com.weatherApp.exception.InvalidPasswordException;
import com.weatherApp.exception.InvalidUsernameException;
import org.springframework.stereotype.Component;

@Component
public class  RegistrationValidator {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 4;

    public void validate(String username, String password) {
        validateUsername(username);
        validatePassword(password);
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new InvalidUsernameException("Username cannot be empty");
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            throw new InvalidUsernameException("Username must be at least " + MIN_USERNAME_LENGTH + " characters");
        }
        if (username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidUsernameException("Username must be at most " + MAX_USERNAME_LENGTH + " characters");
        }
        if (!username.matches("^[a-zA-Z0-9_-]+$")) {
            throw new InvalidUsernameException("Username can only contain letters, numbers, underscores and hyphens");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new InvalidPasswordException("Password cannot be empty");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
        }
    }
}
