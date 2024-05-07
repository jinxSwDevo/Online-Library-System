package com.software2.bookService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.software2.bookService.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book>findById(Long  id);

    Optional<Book> findByTitle(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    Optional<Book> findByISBN(String isbn);

    List<Book> findByRackNumber(Integer racknumber);

    List<Book> findByAuthorContainingIgnoreCaseAndRackNumber(String author, Integer shelfNumber);

    void deleteById(Optional<Book> book);


}
