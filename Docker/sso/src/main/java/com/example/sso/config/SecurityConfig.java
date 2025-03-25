package com.example.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/user/**", "/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/user", true)
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            );
        
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            
            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    Map<String, Object> userInfo = oidcUserAuthority.getUserInfo().getClaims();
                    
                    // Xử lý roles từ Auth0
                    // Auth0 thường trả về roles trong claim "https://dev-xns558dobru7f6t5.us.auth0.com/roles" hoặc "roles"
                    extractRoles(userInfo, "https://dev-xns558dobru7f6t5.us.auth0.com/roles", mappedAuthorities);
                    extractRoles(userInfo, "roles", mappedAuthorities);
                    
                    // Hoặc xử lý permissions từ Auth0
                    extractRoles(userInfo, "https://dev-xns558dobru7f6t5.us.auth0.com/permissions", mappedAuthorities);
                    extractRoles(userInfo, "permissions", mappedAuthorities);
                }
                mappedAuthorities.add(authority);
            });
            
            return mappedAuthorities;
        };
    }
    
    @SuppressWarnings("unchecked")
    private void extractRoles(Map<String, Object> userInfo, String claimName, Set<GrantedAuthority> authorities) {
        if (userInfo.containsKey(claimName)) {
            List<String> roles = (List<String>) userInfo.get(claimName);
            if (roles != null) {
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                }
            }
        }
    }

    // Thêm bean để lưu trữ thông tin người dùng sau đăng nhập nếu cần
    @Bean
    public HttpSessionOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }
}
