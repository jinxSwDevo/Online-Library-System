package com.software2.authenticationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.software2.authenticationService.entity.User;




@Repository
public interface UserRepository extends JpaRepository<User , Long>{

    User findByEmail(String email);
    boolean existsByEmail(String email);
}
