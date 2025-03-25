package com.example.sso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home";
    }
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OidcUser principal, Model model) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("email", principal.getEmail());
        model.addAttribute("attributes", principal.getAttributes());
        return "user";
    }
}
