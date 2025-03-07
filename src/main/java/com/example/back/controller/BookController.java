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
        // 遍历书籍列表，将 book_img 转换为 Base64 格式
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getBook_img());  // 转换为 Base64
                book.setBook_img_url(base64Image);  // 设置 Base64 图片数据
                book.setBook_img(null);  // 清空原始 BLOB 数据，避免返回
            }
        }
        return Result.success(books);  // 返回带有 Base64 图片的书籍列表
    }

    @GetMapping("/{book_id}")
    public Result getBookById(@PathVariable Integer book_id) {
        Book book = bookService.getBookById(book_id);
        if (book == null) {
            return Result.error("Book not found");
        }
        // 将 book_img 转换为 Base64 格式
        if (book.getBook_img() != null) {
            String base64Image = Base64.getEncoder().encodeToString(book.getBook_img());
            book.setBook_img_url(base64Image);  // 设置 Base64 图片数据
            book.setBook_img(null);  // 清空原始 BLOB 数据
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
