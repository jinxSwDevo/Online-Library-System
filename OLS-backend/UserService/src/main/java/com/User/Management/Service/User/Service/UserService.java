package com.User.Management.Service.User.Service;

import java.util.List;

import com.User.Management.Service.User.Entity.User;

public interface UserService {
    
    User getUserById(Long userId);

    User getByUserName(String username);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
    
}