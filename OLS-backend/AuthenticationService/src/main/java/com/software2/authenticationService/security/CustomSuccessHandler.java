package com.software2.authenticationService.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.software2.authenticationService.entity.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    // private SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
    //         new SimpleUrlAuthenticationSuccessHandler("user/home-page");
    // private SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
    //         new SimpleUrlAuthenticationSuccessHandler("admin/dashboard");

    @Override
    public void onAuthenticationSuccess(
                                HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) 
                            throws IOException, ServletException{

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals(Role.LAIBRARIAN.name())) {
                // if the user is an ADMIN delegate to the adminSuccessHandler
                new SimpleUrlAuthenticationSuccessHandler("/admin/home").onAuthenticationSuccess(request, response, authentication);
                return;
            }

        }
        // if the user is not an admin delegate to the userSuccessHandler
        new SimpleUrlAuthenticationSuccessHandler("/user/home").onAuthenticationSuccess(request, response, authentication);
    }
}
