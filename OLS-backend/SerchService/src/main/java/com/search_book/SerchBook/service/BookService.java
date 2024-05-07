package com.search_book.SerchBook.service;

import com.search_book.SerchBook.entity.Book;

public interface BookService {

     Book getBookById(Long userId);
     Book getBookByTitle(String title);
}
