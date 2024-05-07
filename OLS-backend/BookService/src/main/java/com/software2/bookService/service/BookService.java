package com.software2.bookService.service;

import java.util.List;

import com.software2.bookService.dto.BookRequest;
import com.software2.bookService.entity.Book;

public interface BookService {

    Book getBookById(Long bookId);

    Book getBookByISBN(String isbn);

    List<Book> getAllBooks();

    Book addBook(BookRequest book);

    Book updateBook(BookRequest book , Long id);

    void deleteBook(Long userId);
}
