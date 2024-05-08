package com.software2.authenticationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.software2.authenticationService.dto.LoginResponse;
import com.software2.authenticationService.dto.RegisterDto;
import com.software2.authenticationService.entity.Role;
import com.software2.authenticationService.entity.User;
import com.software2.authenticationService.repository.UserRepository;
import com.software2.authenticationService.security.auth.JwtService;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;


    public LoginResponse provideAuth(String email , String password) throws Exception{
        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(auth);

            User user = (User)auth.getPrincipal();
            String jwt = jwtService.generateJwtToken(user);
            Role role = user.getRole();

            return new LoginResponse(jwt , user , role);
        }catch(Exception e){
            throw new Exception("wrong in fetch user");
        }
    }
    
    public void setJwtHeader(String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
    }

    public boolean isUserExist(String email){
        return userRepository.existsByEmail(email);
    }

    public User saveUser(RegisterDto data){
        
        User newUser = new User();
        newUser.setFirstName(data.getFirstName());
        newUser.setLastName(data.getLastName());
        newUser.setPassword(passwordEncoder.encode(data.getPassword()));
        newUser.setEmail(data.getEmail());
        newUser.setRole(Role.USER);

        return  userRepository.save(newUser);
    }

    
}
