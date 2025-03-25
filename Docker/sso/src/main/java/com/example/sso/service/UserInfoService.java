package com.example.sso.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class UserInfoService {

    /**
     * Lấy thông tin người dùng từ context bảo mật hiện tại
     * @return Bản đồ chứa thông tin người dùng
     */
    public Map<String, Object> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken.getPrincipal() instanceof OidcUser) {
                OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
                return oidcUser.getClaims();
            }
        }
        
        return Collections.emptyMap();
    }
    
    /**
     * Kiểm tra xem người dùng có đăng nhập hay không
     * @return true nếu người dùng đã đăng nhập
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && 
               !(authentication.getPrincipal().equals("anonymousUser"));
    }
    
    /**
     * Lấy ID người dùng duy nhất từ Auth0
     * @return ID người dùng hoặc null nếu không tìm thấy
     */
    public String getUserId() {
        Map<String, Object> userInfo = getCurrentUserInfo();
        return userInfo.containsKey("sub") ? (String) userInfo.get("sub") : null;
    }
}
