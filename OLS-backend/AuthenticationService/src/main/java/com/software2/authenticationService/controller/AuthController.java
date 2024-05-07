package com.software2.authenticationService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.software2.authenticationService.dto.LoginDto;
import com.software2.authenticationService.dto.RegisterDto;
import com.software2.authenticationService.entity.User;
import com.software2.authenticationService.service.AuthService;
import com.software2.authenticationService.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(
        @ModelAttribute(name = "newUser") RegisterDto register,
        RedirectAttributes redirect
    ){
        if(authService.isUserExist(register.getEmail())){
            redirect.addFlashAttribute("emailExist","email is already exist");
            return "redirect:/register?emailExist";
        }
        User user = authService.saveUser(register);

        if(user == null){
            redirect.addFlashAttribute("serverError","sorry its probrlem in server.");
            return "redirect:/register?serverError";
        }
        return "redirect:/register-success";
    }

    @PostMapping("/login")
    public String login( 
                    @ModelAttribute(name = "userInter") LoginDto login,
                    RedirectAttributes redirect,
                    HttpServletRequest request) {

                        System.out.println("in login");
        UserDetails user = userService.loadUserByUsername(login.getEmail());
        if(user == null){
            redirect.addFlashAttribute("userNotFound","user not founded");
            return "redirect:/login?userNotFound";
        }else{
            if(!passwordEncoder.matches(login.getPassword(), user.getPassword())){
                redirect.addFlashAttribute("passwordWrong","password is wrong , please try again!");
                return "redirect:/login?passwordWrong";
            }
        }
        
        String jwt = authService.provideToken(login.getEmail(), login.getPassword());
        if(jwt != null){
            authService.setJwtHeader(jwt);
            HttpSession session= request.getSession();
			session.setAttribute("email", user.getUsername());

            return "redirect:/";
        }
        
        return "redirect:/login";
    }
    
}
