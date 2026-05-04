package com.weatherApp.controllers;

import com.weatherApp.exceptions.AuthException;
import com.weatherApp.exceptions.InvalidPasswordException;
import com.weatherApp.exceptions.InvalidUsernameException;
import com.weatherApp.exceptions.UserAlreadyExistsException;
import com.weatherApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private AuthService authService;

    @Value("${session.max.age.seconds:86400}")
    private int SESSION_MAX_AGE_SECONDS;

    @GetMapping("/sign-up")
    public String showSignUpForm() {
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("repeat-password") String repeatPassword,
                                Model model) {

        if(!password.equals(repeatPassword)){
            model.addAttribute("error", "passwords_dont_match");
            return "auth/sign-up-with-errors";
        }
        try {
            authService.register(username, password);
            return "redirect:/sign-in";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", "user_exists");
            return "auth/sign-up-with-errors";
        } catch (InvalidUsernameException e) {
            model.addAttribute("error", "invalid_username");
            return "auth/sign-up-with-errors";
        } catch (InvalidPasswordException e) {
            model.addAttribute("error", "invalid_password");
            return "auth/sign-up-with-errors";
        } catch (AuthException e) {
            model.addAttribute("error", "registration_failed");
            return "auth/sign-up-with-errors";
        }
    }

    @GetMapping("/sign-in")
    public String showSignInForm() {
        return "auth/sign-in";
    }

    @PostMapping("/sign-in")
    public String processSignIn(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpServletResponse response,
                                Model model) {

        try {
            String token = authService.login(username, password);
            Cookie cookie = new Cookie("weather_session", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(SESSION_MAX_AGE_SECONDS);
            response.addCookie(cookie);
            return "redirect:/";
        } catch (AuthException e) {
            model.addAttribute("error", "Invalid username or password");
            return "auth/sign-in-with-errors";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response,
                         @CookieValue(value = "weather_session", required = false) String token) {

        if (token != null) {
            authService.logout(token);
            Cookie cookie = new Cookie("weather_session", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
