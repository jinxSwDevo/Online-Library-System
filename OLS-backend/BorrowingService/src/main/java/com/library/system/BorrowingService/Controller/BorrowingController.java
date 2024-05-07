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

import com.library.system.BorrowingService.entity.BorrowRequest;
import com.library.system.BorrowingService.entity.BorrowedBook;
import com.library.system.BorrowingService.service.BorrowingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long userId, @RequestParam String ISBN) {
        try {
            borrowingService.borrowBook(userId, ISBN);
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
    public ResponseEntity<List<BorrowRequest>> getPendingBorrowRequests() {
        List<BorrowRequest> borrowRequests = borrowingService.getPendingBorrowRequests();
        return ResponseEntity.ok(borrowRequests);
    }

    @PostMapping("/manage-request/{requestId}")
    public ResponseEntity<String> manageBorrowRequest(@PathVariable Long requestId, @RequestParam boolean approve) {
        borrowingService.manageBorrowRequest(requestId, approve);
        return ResponseEntity.ok("Request managed successfully");
    }

    @PostMapping("/return/{borrowId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowId) {
        borrowingService.returnBook(borrowId);
        return ResponseEntity.ok("Book returned successfully");
    } 
}
