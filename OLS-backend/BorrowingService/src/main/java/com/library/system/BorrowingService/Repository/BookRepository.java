package com.library.system.BorrowingService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.system.BorrowingService.entity.Book;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByISBN(String ISBN);
    Optional<Book>  findById(Long id);
}
