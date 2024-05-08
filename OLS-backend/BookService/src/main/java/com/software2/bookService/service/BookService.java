package com.software2.bookService.service;

import java.util.List;

import com.software2.bookService.dto.BookRequest;
import com.software2.bookService.entity.Book;

public interface BookService {

    Book getBookById(Long bookId);

    Book getBookByISBN(String isbn);

    List<Book> getAllBooks();

    Book addBook(BookRequest book);

    Book updateBook(BookRequest book , Long id) throws Exception;

    void deleteBook(Long userId);

    List<Book> filterBooksByAuthor(String author);

    List<Book> filterBooksByRackNumber(Integer rackNumber);

    List<Book> filterBooksByAuthorAndRackNumber(String author, Integer rackNumber);
}
