package com.search_book.SerchBook.service;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.search_book.SerchBook.entity.Book;
import com.search_book.SerchBook.repository.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.orElse(null);  
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}


/*@Override
public Book getBookById(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    return optionalBook.get();
}*/