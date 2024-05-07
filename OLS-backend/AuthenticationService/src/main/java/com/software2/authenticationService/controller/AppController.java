package com.software2.authenticationService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.software2.authenticationService.dto.LoginDto;
import com.software2.authenticationService.dto.RegisterDto;
import com.software2.authenticationService.entity.Role;
import com.software2.authenticationService.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class AppController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("newUser", new RegisterDto());
        return "register-page";
    }

    @GetMapping("/register-success")
    public String registerSuccess(){
        return "register-success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("userInter", new LoginDto());
        return "login-page";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        return "redirect:/login-page?logout";
    }

    @GetMapping({"" , "/"})
	public String home(Model model, HttpServletRequest request) {
		HttpSession session= request.getSession();
		String email=(String)session.getAttribute("email");
        
		if(email==null) {
            return "redirect:/login";
		}
        var user = userService.loadUserByUsername(email);
        
        for (GrantedAuthority authority : user.getAuthorities()) {
            String role = authority.getAuthority();
            if(role == Role.LAIBRARIAN.name())
                return "admin/home";
        }
        
        return "user/home";
	}
    
}
