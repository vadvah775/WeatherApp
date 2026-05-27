package com.weatherApp.service;

import com.weatherApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthService {
    @Transactional
    void register(String login, String rawPassword);

    @Transactional
    String login(String login, String rawPassword);

    @Transactional
    void logout(String token);

    @Transactional
    Optional<User> getUserByToken(String token);
}
