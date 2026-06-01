package com.weatherApp.service;

import com.weatherApp.dto.CurrentUserDto;

import java.util.Optional;

public interface AuthService {
    void register(String login, String rawPassword);

    String login(String login, String rawPassword);

    void logout(String token);

    Optional<CurrentUserDto> getUserByToken(String token);

    void cleanExpiredSessions();
}
