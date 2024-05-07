package com.library.system.BorrowingService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.BorrowingService.entity.BorrowRequest;
import com.library.system.BorrowingService.entity.RequestStatus;
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, Long> {
    List<BorrowRequest> findByStatus(RequestStatus status);
}