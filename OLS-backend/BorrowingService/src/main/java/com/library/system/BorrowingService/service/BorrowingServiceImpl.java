package com.library.system.BorrowingService.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.system.BorrowingService.Repository.BookRepository;
import com.library.system.BorrowingService.Repository.BorrowRequestRepository;
import com.library.system.BorrowingService.Repository.BorrowedBookRepository;
import com.library.system.BorrowingService.entity.Book;
import com.library.system.BorrowingService.entity.BorrowRequest;
import com.library.system.BorrowingService.entity.BorrowedBook;
import com.library.system.BorrowingService.entity.RequestStatus;
import com.library.system.BorrowingService.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BorrowRequestRepository borrowRequestRepository;

    @Override
    public void borrowBook(Long userId, String ISBN) throws Exception {
        Book book = bookRepository.findByISBN(ISBN);
        if (book == null || book.getQuantityAvailable() <= 0) {
            throw new Exception("Book not available for borrowing");
        }

        // Decrease book quantity
        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        bookRepository.save(book);

        // Record borrowing
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(new User()); // Assuming User constructor takes ID
        borrowedBook.setBook(book);
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBook.setReturnDate(determineDueDate(LocalDate.now()));
        borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public List<BorrowedBook> getBorrowedBooksForUser(Long userId) {
        return borrowedBookRepository.findByUserId(userId);
    }

    @Override
    public List<BorrowRequest> getPendingBorrowRequests() {
        return borrowRequestRepository.findByStatus(RequestStatus.PENDING);
    }

    @Override
    public void manageBorrowRequest(Long requestId, boolean approve) {
        BorrowRequest request = borrowRequestRepository.findById(requestId).orElse(null);
        if (request != null) {
            if (approve) {
                borrowBook(request.getISBN());
                request.setStatus(RequestStatus.APPROVED);
            } else {
                request.setStatus(RequestStatus.REJECTED);
            }
            borrowRequestRepository.save(request);
        }
    }

    private void borrowBook(String isbn) {
        throw new UnsupportedOperationException("Unimplemented method 'borrowBook'");
    }

    @Override
    public LocalDate determineDueDate(LocalDate borrowDate) {
        // Example: Due date is 14 days from borrowing
        return borrowDate.plusDays(14);
    }

    @Override
    public void returnBook(Long borrowId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowId).orElse(null);
        if (borrowedBook != null) {
            borrowedBookRepository.delete(borrowedBook);
            Book book = borrowedBook.getBook();
            book.setQuantityAvailable(book.getQuantityAvailable() + 1);
            bookRepository.save(book);
        }
    }
}
