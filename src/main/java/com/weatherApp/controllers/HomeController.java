package com.weatherApp.controllers;

import com.weatherApp.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        User user = (User) request.getAttribute("currentUser");
        if (user != null) {
            model.addAttribute("username", user.getLogin());
        }
        return "index";
    }
}