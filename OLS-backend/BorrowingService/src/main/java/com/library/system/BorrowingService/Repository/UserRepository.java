package com.library.system.BorrowingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.BorrowingService.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
