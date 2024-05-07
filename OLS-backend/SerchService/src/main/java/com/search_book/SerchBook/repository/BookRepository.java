package com.search_book.SerchBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.search_book.SerchBook.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

    
}
