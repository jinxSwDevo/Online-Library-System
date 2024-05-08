package com.software2.bookService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software2.bookService.dto.BookRequest;
import com.software2.bookService.entity.Book;
import com.software2.bookService.service.BookService;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping({"","/"})
    public ResponseEntity<List<Book>> viewBookList() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showBookDetails(@PathVariable(name = "id") Long id,Model model) {
        Book currentBook = bookService.getBookById(id);
        model.addAttribute("book", currentBook);
        return ResponseEntity.ok().body(currentBook);
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest book , Model model) {
        System.out.println(book);
        Book newBook = bookService.addBook(book);
        return ResponseEntity.ok().body(newBook);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookRequest book ,@PathVariable(name="id") Long id) {
        try{
            Book newBook = bookService.updateBook(book , id);
            return ResponseEntity.ok().body(newBook);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("error to find book " + e);
        }
    }

    @DeleteMapping("/remove/{id}")
	public ResponseEntity<?> removeBook(@PathVariable(name = "id") Long id,Model model) {
		Book book = bookService.getBookById(id);
		if(book == null) {
            return ResponseEntity.badRequest().body("book not founded");
		}
        bookService.deleteBook(id);
		return ResponseEntity.ok().body("book deleted succesfuly");
	}

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> filterBooks(@RequestParam(required = false) String author, @RequestParam(required = false) Integer rackNumber) {
        List<Book> books;
        if (author != null && rackNumber != null) {
            // Filter by both author and shelfNumber
            books = bookService.filterBooksByAuthorAndRackNumber(author, rackNumber);
        } else if (author != null) {
            // Filter only by author
            books= bookService.filterBooksByAuthor(author);
        } else if (rackNumber != null) {
            // Filter only by shelfNumber
            books = bookService.filterBooksByRackNumber(rackNumber);
        } else {
            // No filter parameters provided, return all books
            books = bookService.getAllBooks();
        }
        return ResponseEntity.ok().body(books);
    }

}
