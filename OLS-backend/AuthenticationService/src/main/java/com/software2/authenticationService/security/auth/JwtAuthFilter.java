package com.software2.authenticationService.security.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.software2.authenticationService.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Configuration
public class JwtAuthFilter extends OncePerRequestFilter{

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull  FilterChain filterChain)
            throws ServletException, IOException {

            if(!jwtService.isJWTTokenFound(request)){
                filterChain.doFilter(request, response);
                return;
            }
            
            try{
                String jwtToken = jwtService.getJwtFromHeader(request);
                String userEmail = jwtService.getUserNameFromJwtToken(jwtToken);
                if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userService.loadUserByUsername(userEmail);
                    if(jwtService.isJWTTokenValid(jwtToken, userDetails)){
                        //update security auth
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetails(request));
                        //update security holder
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }catch (Exception e) {
                throw new BadCredentialsException("invalid token");
            }

            filterChain.doFilter(request,response);
    }

    
}
