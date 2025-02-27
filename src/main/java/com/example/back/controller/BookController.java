package com.example.back.controller;

import com.example.back.pojo.Book;
import com.example.back.pojo.Result;
import com.example.back.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public Result listBooks() {
        List<Book> books = bookService.listBooks();
        return Result.success(books);
    }

    @GetMapping("/{book_id}")
    public Result getBookById(@PathVariable Integer book_id) {
        Book book = bookService.getBookById(book_id);
        if (book == null) {
            return Result.error("Book not found");
        }
        return Result.success(book);
    }

    @PostMapping("/add")
    public Result addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return Result.success();
    }

    @DeleteMapping("/delete/{book_id}")
    public Result deleteBook(@PathVariable Integer book_id) {
        bookService.deleteBook(book_id);
        return Result.success();
    }

    @GetMapping("/type/{book_type}")
    public Result getBooksByType(@PathVariable String book_type) {
        List<Book> books = bookService.getBooksByType(book_type);
        return Result.success(books);
    }

}
