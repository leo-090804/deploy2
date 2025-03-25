package com.example.sso.controller;

import com.example.sso.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/account")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal OidcUser principal, Model model) {
        // Thông tin cơ bản
        model.addAttribute("userId", principal.getSubject());
        model.addAttribute("username", principal.getName());
        model.addAttribute("email", principal.getEmail());
        
        // Thông tin claims từ Auth0
        Map<String, Object> claims = principal.getClaims();
        model.addAttribute("claims", claims);
        
        // Thông tin về phiên đăng nhập
        model.addAttribute("issuedAt", principal.getIssuedAt());
        model.addAttribute("expiresAt", principal.getExpiresAt());
        model.addAttribute("idToken", principal.getIdToken().getTokenValue());
        
        return "user-profile";
    }
    
    @GetMapping("/settings")
    public String showUserSettings(Model model) {
        // Chỉ thêm một số thông tin cơ bản vào model
        model.addAttribute("authenticated", userInfoService.isAuthenticated());
        model.addAttribute("userId", userInfoService.getUserId());
        return "user-settings";
    }
}
