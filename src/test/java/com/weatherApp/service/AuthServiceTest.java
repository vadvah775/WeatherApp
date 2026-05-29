package com.weatherApp.service;

import com.weatherApp.BaseIntegrationTest;
import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.exception.AuthException;
import com.weatherApp.exception.UserAlreadyExistsException;
import com.weatherApp.entity.Session;
import com.weatherApp.entity.User;
import com.weatherApp.repository.SessionRepository;
import com.weatherApp.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

public class AuthServiceTest extends BaseIntegrationTest {

    private AuthService authService;

    private UserRepository userRepository;

    private SessionRepository sessionRepository;

    @Value("${session.max.age.hours:0}")
    private long sessionHours;

    // register

    @Test
    public void register_NewUser_success() {
        String login = "newuser";
        String password = "1234";

        authService.register(login, password);

        Optional<User> optUser = userRepository.findByLogin(login);
        assertTrue(optUser.isPresent());
        assertEquals(login, optUser.get().getLogin());
        assertNotNull(optUser.get().getPassword());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void register_ExistingUser_throwsException() {
        String login = "login";
        String password = "1234";

        authService.register(login, password);
        authService.register(login, password);
    }

    // login

    @Test
    public void login_validCredentials_success() {
        String login = "login";
        String password = "1234";
        authService.register(login, password);

        String token = authService.login(login, password);
        assertNotNull(token);
        assertFalse(token.isBlank());

        Optional<Session> optSession = sessionRepository.findById(UUID.fromString(token));
        assertTrue(optSession.isPresent());
        assertEquals(login, optSession.get().getUser().getLogin());
    }

    @Test(expected = AuthException.class)
    public void login_invalidCredentials_throwsException() {
        String login = "login";
        authService.register(login, "correct");
        authService.login(login, "wrong");
    }

    @Test(expected = AuthException.class)
    public void login_NonexistentUser_throwsException() {
        authService.login("unknown", "pass");
    }

    // session token

    @Test
    public void getUserByToken_ExpiredToken_ReturnsEmpty() {
        String login = "tokenUser";
        String password = "pass";
        authService.register(login, password);
        String token = authService.login(login, password);

        Optional<CurrentUserDto> optUser = authService.getUserByToken(token);
        assertFalse(optUser.isPresent());
    }

    @Test
    public void getUserByToken_InvalidToken_ReturnsEmpty() {
        Optional<CurrentUserDto> optUser = authService.getUserByToken("not-a-uuid");
        assertFalse(optUser.isPresent());
    }


    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
}
