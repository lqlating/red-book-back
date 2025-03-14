package com.example.back.controller;

import com.example.back.pojo.Book;
import com.example.back.pojo.Result;
import com.example.back.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public Result listBooks() {
        List<Book> books = bookService.listBooks();
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
        return Result.success(books);
    }

    @GetMapping("/{book_id}")
    public Result getBookById(@PathVariable Integer book_id) {
        Book book = bookService.getBookById(book_id);
        if (book == null) {
            return Result.error("Book not found");
        }
        // 将 book_img 转换为 base64 并存储到 book_img_base64 字段
        if (book.getBook_img() != null) {
            String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
            book.setBook_img_base64(book_img_base64);
        }
        return Result.success(book);
    }

    @PostMapping("/add")
    public Result addBook(@RequestBody Book book) {
        // 将 Base64 图片转换为字节数组
        if (book.getBook_img_base64() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(book.getBook_img_base64());
            book.setBook_img(imageBytes); // 设置为字节数组
            book.setBook_img_base64(null); // 清空 Base64 数据，避免冗余
        }
        bookService.addBook(book);
        return Result.success("Book added successfully");
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
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
        return Result.success(books);
    }

    // 新增接口定义
    @GetMapping("/search")
    public Result getBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.getBooksByTitle(title);
        return Result.success(books);
    }

    // 新增接口定义
    @GetMapping("/search/title")
    public Result getBooksByTitleContaining(@RequestParam String title) {
        List<Book> books = bookService.getBooksByTitleContaining(title);
        return Result.success(books);
    }
}