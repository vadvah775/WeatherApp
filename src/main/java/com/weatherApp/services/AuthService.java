package com.weatherApp.services;

import com.weatherApp.exceptions.AuthException;
import com.weatherApp.exceptions.UserAlreadyExistsException;
import com.weatherApp.models.Session;
import com.weatherApp.models.User;
import com.weatherApp.repositories.SessionRepository;
import com.weatherApp.repositories.UserRepository;
import com.weatherApp.util.PasswordEncoder;
import com.weatherApp.util.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private PasswordEncoder passwordEncoder;

    private RegistrationValidator registrationValidator;

    private static final long SESSION_HOURS = 24;

    @Transactional
    public void register(String login, String rawPassword) {
        registrationValidator.validate(login, rawPassword);

        if (userRepository.findByLogin(login).isPresent()){
            throw new UserAlreadyExistsException(login);
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    @Transactional
    public String login(String login, String rawPassword) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }

        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now().plusHours(SESSION_HOURS));
        sessionRepository.save(session);
        return session.getId().toString(); // token
    }

    @Transactional
    public void logout(String token) {
        if (token == null) return;
        try{
            UUID uuid = UUID.fromString(token);
            sessionRepository.findById(uuid).ifPresent(sessionRepository::delete);
        } catch (IllegalArgumentException ignored){}
    }

    @Transactional
    public Optional<User> getUserByToken(String token) {
        if(token == null) return Optional.empty();
        try {
            UUID uuid = UUID.fromString(token);
            return sessionRepository.findById(uuid)
                    .filter(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
                    .map(Session::getUser);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRegistrationValidator(RegistrationValidator registrationValidator) {
        this.registrationValidator = registrationValidator;
    }


}
