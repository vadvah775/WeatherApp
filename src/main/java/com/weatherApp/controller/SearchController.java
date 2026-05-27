package com.weatherApp.controller;

import com.weatherApp.dto.GeocodingResponse;
import com.weatherApp.entity.User;
import com.weatherApp.service.OpenWeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private OpenWeatherService openWeatherService;

    @GetMapping("/search")
    public String search(@RequestParam("name") String query, Model model, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");

        if (user != null) model.addAttribute("username", user.getLogin());
        List<GeocodingResponse> results = openWeatherService.searchLocations(query);
        model.addAttribute("results", results);
        model.addAttribute("query", query);
        return "search-results";
    }

    @Autowired
    public void setOpenWeatherService(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }
}
