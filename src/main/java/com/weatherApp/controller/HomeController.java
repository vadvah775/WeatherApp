package com.weatherApp.controller;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.dto.WeatherDisplayDto;
import com.weatherApp.service.WeatherDisplayService;
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
        CurrentUserDto currentUser = (CurrentUserDto) request.getAttribute("currentUser");
        List<WeatherDisplayDto> weatherList;

        if (currentUser == null) {
            weatherList = weatherDisplayService.getWeatherForGuest();
        } else {
            weatherList = weatherDisplayService.getWeatherForUser(currentUser);
        }

        model.addAttribute("weatherList", weatherList);
        return "index";
    }

    @Autowired
    public void setWeatherDisplayService(WeatherDisplayService weatherDisplayService) {
        this.weatherDisplayService = weatherDisplayService;
    }
}