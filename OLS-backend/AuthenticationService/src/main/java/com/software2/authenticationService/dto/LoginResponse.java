package com.software2.authenticationService.dto;

import com.software2.authenticationService.entity.Role;
import com.software2.authenticationService.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String jwt;
    private User user;
    private Role role;
}