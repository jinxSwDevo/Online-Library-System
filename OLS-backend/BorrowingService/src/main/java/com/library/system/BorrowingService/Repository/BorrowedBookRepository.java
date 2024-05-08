package com.library.system.BorrowingService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.BorrowingService.entity.BorrowedBook;
import com.library.system.BorrowingService.entity.RequestStatus;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByUserId(Long userId);
    List<BorrowedBook> findByStatus(RequestStatus status);
}
