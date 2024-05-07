package com.User.Management.Service.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.User.Management.Service.User.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}