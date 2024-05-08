package com.library.System.Service.UserService.Service;

import java.util.List;

import com.library.System.Service.UserService.Entity.User;



public interface UserService {
    
    User getUserById(Long userId);

    User getByUserName(String username);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
    
}