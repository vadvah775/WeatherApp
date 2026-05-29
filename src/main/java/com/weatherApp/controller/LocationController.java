package com.weatherApp.controller;

import com.weatherApp.dto.CurrentUserDto;
import com.weatherApp.entity.User;
import com.weatherApp.service.LocationService;
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
        CurrentUserDto currentUser = (CurrentUserDto) request.getAttribute("currentUser");// TODO поменять user -> dto
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        boolean added = locationService.addLocationOfUser(currentUser, lat, lon, name);

        if (added) {
            redirectAttributes.addFlashAttribute("success", "Location added!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Location already exists.");
        }

        return "redirect:/";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(
                    @RequestParam("locationId") long locationId,
                    HttpServletRequest request,
                    RedirectAttributes redirectAttributes) {

        CurrentUserDto currentUser = (CurrentUserDto) request.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/sign-in";
        }

        if (locationService.deleteLocationOfUser(currentUser, locationId)) {
            redirectAttributes.addFlashAttribute("success", "Location deleted!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Location not found or access denied");
        }

        return "redirect:/";
    }


    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }
}
