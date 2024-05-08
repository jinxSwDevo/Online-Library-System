package com.software2.authenticationService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.software2.authenticationService.dto.LoginDto;
import com.software2.authenticationService.dto.LoginResponse;
import com.software2.authenticationService.dto.RegisterDto;
import com.software2.authenticationService.entity.User;
import com.software2.authenticationService.service.AuthService;
import com.software2.authenticationService.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto register){
        if(authService.isUserExist(register.getEmail())){
           return ResponseEntity.badRequest().body("email is already exist");
        }
        User user = authService.saveUser(register);
        if(user == null){
            return ResponseEntity.badRequest().body("sorry its probrlem in server.");
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestBody LoginDto login) throws Exception {
        UserDetails user = userService.loadUserByUsername(login.getEmail());
        if(user == null){
            return ResponseEntity.badRequest().body("email not founded");
        }
        if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("password not correct");
        }
        LoginResponse response = authService.provideAuth(login.getEmail(), login.getPassword());
        return ResponseEntity.ok().body(response);
    }
}
