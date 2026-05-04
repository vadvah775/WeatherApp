package com.weatherApp.controllers;

import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.models.User;
import com.weatherApp.services.WeatherDisplayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private WeatherDisplayService weatherDisplayService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        User user = (User) request.getAttribute("currentUser");
        List<WeatherDisplayDto> weatherList;

        if (user == null) {
            weatherList = weatherDisplayService.getWeatherForGuest();
        } else {
            weatherList = weatherDisplayService.getWeatherForUser(user);
            model.addAttribute("username", user.getLogin());
        }

        model.addAttribute("weatherList", weatherList);
        return "index";
    }

    @Autowired
    public void setWeatherDisplayService(WeatherDisplayService weatherDisplayService) {
        this.weatherDisplayService = weatherDisplayService;
    }
}