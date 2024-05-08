package com.library.system.BorrowingService.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.system.BorrowingService.Repository.BookRepository;
import com.library.system.BorrowingService.Repository.BorrowedBookRepository;
import com.library.system.BorrowingService.Repository.UserRepository;
import com.library.system.BorrowingService.entity.Book;
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
    private UserRepository userRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;


    @Override
    public void borrowBook(Long userId, Long bookId) throws Exception {
        Book book = bookRepository.findById(bookId).orElse(null);
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("user not founded"));
        if (book == null || book.getNoCopies() <= 0) {
            throw new Exception("Book not available for borrowing");
        }

        // Record borrowing
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUser(user); // Assuming User constructor takes ID
        borrowedBook.setBook(book);
        borrowedBook.setStatus(RequestStatus.PENDING);
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBook.setReturnDate(determineDueDate(LocalDate.now()));
        borrowedBookRepository.save(borrowedBook);
    }

    

    @Override
    public List<BorrowedBook> getBorrowedBooksForUser(Long userId) {
        return borrowedBookRepository.findByUserId(userId);
    }

    @Override
    public List<BorrowedBook> getPendingBorrowRequests() {
        return borrowedBookRepository.findByStatus(RequestStatus.PENDING);
    }

    @Override
    public void manageBorrowRequest(Long borrowId, boolean approve) {
        BorrowedBook request = borrowedBookRepository.findById(borrowId).orElse(null);
        if (request != null) {
            if (approve) {
                request.setStatus(RequestStatus.APPROVED); 
                manageNoCopies(request.getBook(), !approve);
            } else {
                request.setStatus(RequestStatus.REJECTED);
                manageNoCopies(request.getBook(), !approve);
            }
            borrowedBookRepository.save(request);
        }
    }



    private void manageNoCopies(Book book , boolean increase){
        if(increase){
            book.setNoCopies(book.getNoCopies() + 1);
        }else{
            book.setNoCopies(book.getNoCopies() - 1);
        }
        bookRepository.save(book);
    }

    public void deleteBorrow(Long borrowId){
        borrowedBookRepository.deleteById(borrowId);
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
            Book book = borrowedBook.getBook();
            manageNoCopies(book, true);
            deleteBorrow(borrowId);
            bookRepository.save(book);
        }else{
            System.out.println("book returned");
        }
    }
}
