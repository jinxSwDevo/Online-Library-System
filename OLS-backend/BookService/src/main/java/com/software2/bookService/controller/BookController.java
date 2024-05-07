package com.software2.bookService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.software2.bookService.dto.BookRequest;
import com.software2.bookService.entity.Book;
import com.software2.bookService.service.BookService;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping({"","/"})
    public String viewBookList(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "user/list-books";
    }

    @GetMapping("admin/list")
    public String viewBookListAdmin(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "admin/list-books";
    }
    
    @GetMapping("/{id}")
    public String showBookDetails(
        @PathVariable(name = "id") Long id,
        Model model) {
        
        Book currentBook = bookService.getBookById(id);
        model.addAttribute("book", currentBook);
        return "/user/book-page";
    }

    @GetMapping("/updatePage/{id}")
    public String showUpdatePage(
        @PathVariable(name="id") Long id,
        Model model) {
        
        Book currentBook = bookService.getBookById(id);
        model.addAttribute("book", currentBook);
        return "/admin/update-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@RequestBody BookRequest book ,@PathVariable(name="id") Long id, Model model) {
        Book newBook = bookService.updateBook(book , id);
        if(newBook == null){
            model.addAttribute("error", "sorry update unsucess");
            return "redirect:/admin/update-book";
        }
        model.addAttribute("success", "update sucess");
        return "redirect:/admin/list-book";
    }

    @GetMapping("/addPage")
    public String showaddBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "/admin/add-book";
    }

    @DeleteMapping("/remove/{id}")
	public String removeBook(
            @PathVariable(name = "id") Long id,
            Model model) {

		Book book = bookService.getBookById(id);
		if(book == null) {
            model.addAttribute("error", "error in database , cant find book");
            return "redirect:/admin/list-books";
		}
        bookService.deleteBook(id);
        model.addAttribute("success", "book has been deleted");
		return "redirect:/admin/list-books";
	}

    @PostMapping("/save")
    public String saveBook(@RequestBody BookRequest book , Model model) {
        bookService.addBook(book);
        return "/admin/list-books";
    }
    
    
}
