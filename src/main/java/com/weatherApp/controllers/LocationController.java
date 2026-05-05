package com.weatherApp.controllers;

import com.weatherApp.models.User;
import com.weatherApp.services.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocationController {

    private LocationService locationService;

    @PostMapping("/locations/add")
    public String addLocation(
            @RequestParam("name") String name,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        User currentUser = (User) request.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        boolean added = locationService.addLocationToUser(currentUser, lat, lon, name);

        if (added){
            redirectAttributes.addFlashAttribute("success", "Location added!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Location already exists.");
        }

        return "redirect:/";
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }
}
