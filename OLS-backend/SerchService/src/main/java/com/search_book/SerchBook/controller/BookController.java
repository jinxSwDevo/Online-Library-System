package com.search_book.SerchBook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.search_book.SerchBook.entity.Book;
import com.search_book.SerchBook.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/books")
public class BookController {
    private BookService bookService;

    // build get search by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long bookId){
        Book Book = bookService.getBookById(bookId);
        return new ResponseEntity<>(Book, HttpStatus.OK);
    }
     // build get search by Title REST API
    @GetMapping("/byTitle/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable("title") String title) {
        Book book = bookService.getBookByTitle(title);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
}
}
