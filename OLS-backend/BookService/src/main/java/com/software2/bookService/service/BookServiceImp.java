package com.software2.bookService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software2.bookService.dto.BookRequest;
import com.software2.bookService.entity.Book;
import com.software2.bookService.repository.BookRepository;

@Service
public class BookServiceImp implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(BookRequest book) {
        Book newBook = new Book();
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        newBook.setNocopies(book.getNocopies());
        if(book.getNocopies() <= 0){
            newBook.setAvailibilty(false);
        }
        newBook.setAvailibilty(false);
        newBook.setRackNumber(book.getRackNumber());
        newBook.setISBN(book.getISBN());
        return bookRepository.save(newBook);
    }

    @Override
    public void deleteBook(Long bookId){
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBook(BookRequest book , Long id) {
        Optional<Book> optionalExistingBook = bookRepository.findById(id);
        if (optionalExistingBook.isPresent()) {
            Book existingBook = optionalExistingBook.get();
            existingBook.setRackNumber(book.getRackNumber());
            existingBook.setISBN(book.getISBN());
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            return bookRepository.save(existingBook);
        } else {
            throw new IllegalArgumentException("Book with ID " + id + " not found");
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByISBN(String isbn) {
        Optional<Book> optionalBook = bookRepository.findByISBN(isbn);
        return optionalBook.orElse(null);
    }

    @Override
    public Book getBookById(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        return optionalBook.orElse(null);
    }

    public Book getOne(Long id){
        Optional<Book>  Book = this.bookRepository.findById(id);
        if(Book.isPresent())
            return Book.get();
        return null;
    }
}
