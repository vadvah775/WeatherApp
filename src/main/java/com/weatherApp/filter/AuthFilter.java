package com.weatherApp.filter;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.exception.OpenWeatherApiException;
import com.weatherApp.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> "weather_session".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if(token != null) {
            Optional<CurrentUserDto> user = authService.getUserByToken(token);
            user.ifPresent(currentUserDto -> request.setAttribute("currentUser", currentUserDto));
        }

        try {
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            if (e.getCause() instanceof OpenWeatherApiException){
                log.warn("Error when calling external API", e);
                handleError(request, response, "Couldn't get data from an external API. Please try again later.");
            } else {
                log.error("Error when running the servlet", e);
                handleError(request, response, "We're sorry, but an unexpected error has occurred. Please try again later.");
            }
        } catch (Exception e) {
            log.error("Unexpected error in filter", e);
            handleError(request, response, "We're sorry, but an unexpected error has occurred. Please try again later.");
        }
    }

    private void handleError(HttpServletRequest request,
                              HttpServletResponse response,
                              String errorMessage) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error").forward(request, response);
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
