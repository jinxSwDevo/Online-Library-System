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
        newBook.setNoCopies(book.getNoCopies());
        newBook.setRackNumber(book.getRackNumber());
        newBook.setISBN(book.getIsbn());
        return bookRepository.save(newBook);
    }

    @Override
    public void deleteBook(Long bookId){
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBook(BookRequest book , Long id) throws Exception{
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setRackNumber(book.getRackNumber());
            existingBook.setISBN(book.getIsbn());
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

    public List<Book> filterBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> filterBooksByRackNumber(Integer rackNumber) {
        return bookRepository.findByRackNumber(rackNumber);
    }

    public List<Book> filterBooksByAuthorAndRackNumber(String author, Integer rackNumber) {
        return bookRepository.findByAuthorContainingIgnoreCaseAndRackNumber(author, rackNumber);
    }
}
