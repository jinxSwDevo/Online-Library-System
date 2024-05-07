package com.library.system.BorrowingService.service;

import java.time.LocalDate;
import java.util.List;

import com.library.system.BorrowingService.entity.BorrowRequest;
import com.library.system.BorrowingService.entity.BorrowedBook;

public interface BorrowingService {
    void borrowBook(Long userId, String ISBN) throws Exception;
    List<BorrowedBook> getBorrowedBooksForUser(Long userId);
    List<BorrowRequest> getPendingBorrowRequests();
    void manageBorrowRequest(Long requestId, boolean approve);
    LocalDate determineDueDate(LocalDate borrowDate);
    void returnBook(Long borrowId);
}
