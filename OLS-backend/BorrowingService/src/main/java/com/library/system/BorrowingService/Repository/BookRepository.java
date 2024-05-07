package com.library.system.BorrowingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.BorrowingService.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByISBN(String ISBN);
}
