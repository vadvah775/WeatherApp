package com.weatherApp.service.impl;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.exception.AuthException;
import com.weatherApp.exception.UserAlreadyExistsException;
import com.weatherApp.entity.Session;
import com.weatherApp.entity.User;
import com.weatherApp.filter.AuthFilter;
import com.weatherApp.repository.SessionRepository;
import com.weatherApp.repository.UserRepository;
import com.weatherApp.service.AuthService;
import com.weatherApp.util.PasswordEncoder;
import com.weatherApp.util.RegistrationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    private PasswordEncoder passwordEncoder;

    private RegistrationValidator registrationValidator;

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Value("${session.max.age.hours:24}")
    private long SESSION_HOURS;

    @Transactional
    @Override
    public void register(String login, String rawPassword) {
        registrationValidator.validate(login, rawPassword);

        if (userRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException(login);
        }

        User user = new User(login, passwordEncoder.encode(rawPassword));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public String login(String login, String rawPassword) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AuthException("Error when finding the user by username"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new AuthException("Password does not match password from the database");
        }

        Session session = new Session(user, LocalDateTime.now().plusHours(SESSION_HOURS));
        sessionRepository.save(session);
        return session.getId().toString(); // token
    }

    @Transactional
    @Override
    public void logout(String token) {
        if (token == null) return;
        try {
            UUID uuid = UUID.fromString(token);
            sessionRepository.findById(uuid).ifPresent(sessionRepository::delete);
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Transactional
    @Override
    public Optional<CurrentUserDto> getUserByToken(String token) {
        if (token == null) return Optional.empty();
        try {
            UUID uuid = UUID.fromString(token);
            return sessionRepository.findById(uuid)
                    .filter(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
                    .map(Session::getUser)
                    .map(user -> new CurrentUserDto(user.getLogin()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Scheduled(cron = "0 0 */1 * * *")
    @Transactional
    @Override
    public void cleanExpiredSessions() {
        int countOfDeletedSessions = sessionRepository.deleteExpiredSessions();
        log.info("{}sessions deleted", countOfDeletedSessions);
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
