package com.library.system.BorrowingService.service;

import java.time.LocalDate;
import java.util.List;

import com.library.system.BorrowingService.entity.BorrowedBook;

public interface BorrowingService {
    void borrowBook(Long userId, Long bookId) throws Exception;
    List<BorrowedBook> getBorrowedBooksForUser(Long userId);
    List<BorrowedBook> getPendingBorrowRequests();
    void manageBorrowRequest(Long requestId, boolean approve);
    void deleteBorrow(Long borrowId);
    LocalDate determineDueDate(LocalDate borrowDate);
    void returnBook(Long borrowId);
}
