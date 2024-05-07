package com.software2.authenticationService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.software2.authenticationService.entity.User;
import com.software2.authenticationService.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("user mot founded");
        }
        return user;
    }

}
