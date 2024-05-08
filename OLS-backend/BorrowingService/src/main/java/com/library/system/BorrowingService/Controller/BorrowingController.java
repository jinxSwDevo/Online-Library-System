package com.library.system.BorrowingService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.system.BorrowingService.entity.BorrowedBook;
import com.library.system.BorrowingService.service.BorrowingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/do-borrow/{userId}/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable(value = "userId") Long userId, @PathVariable(value = "bookId") Long bookId) {
        try {
            borrowingService.borrowBook(userId, bookId);
            return ResponseEntity.ok("Book borrowed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BorrowedBook>> getBorrowedBooksForUser(@PathVariable Long userId) {
        List<BorrowedBook> borrowedBooks = borrowingService.getBorrowedBooksForUser(userId);
        return ResponseEntity.ok(borrowedBooks);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<BorrowedBook>> getPendingBorrowRequests() {
        List<BorrowedBook> borrowRequests = borrowingService.getPendingBorrowRequests();
        return ResponseEntity.ok(borrowRequests);
    }

    @PostMapping("/manage-request/{borrowId}")
    public ResponseEntity<String> manageBorrowRequest(@PathVariable Long borrowId, @RequestParam boolean approve) {
        borrowingService.manageBorrowRequest(borrowId, approve);
        return ResponseEntity.ok("Request managed successfully");
    }

    @PostMapping("/delete/{borrowId}")
    public ResponseEntity<String> deleteBorrow(@PathVariable Long borrowId) {
        borrowingService.deleteBorrow(borrowId);
        return ResponseEntity.ok("Book deleted successfully");
    } 

    @PostMapping("/return/{borrowId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowId) {
        borrowingService.returnBook(borrowId);
        return ResponseEntity.ok("Book returned successfully");
    } 
}
