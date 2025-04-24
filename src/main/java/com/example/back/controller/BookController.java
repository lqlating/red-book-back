package com.example.back.controller;

import com.example.back.pojo.Book;
import com.example.back.pojo.Result;
import com.example.back.service.BookService;
import com.example.back.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReportService reportService;

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
        Book book = (Book) bookService.getBookById(book_id);
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

    @PostMapping("/addBook")
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

    // 新增接口：将指定书籍的 is_review 和 is_banned 设置为 1
    @Operation(summary = "Set book as reviewed and banned", description = "Sets the is_review and is_banned fields of the specified book to 1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PutMapping("/setReviewedAndBanned/{book_id}")
    public Result setReviewedAndBanned(@PathVariable Integer book_id) {
        bookService.setReviewedAndBanned(book_id);
        return Result.success("Book set as reviewed and banned successfully");
    }

    @Operation(summary = "Get unreviewed books", description = "Gets all books with is_review = 0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved unreviewed books",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Book.class)) })
    })
    @GetMapping("/unreviewed")
    public Result getUnreviewedBooks() {
        List<Book> books = bookService.getUnreviewedBooks();
        return Result.success(books);
    }

    @Operation(summary = "Get banned books", description = "Gets all books with is_banned = 1")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved banned books",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Book.class)) })
    })
    @GetMapping("/banned")
    public Result getBannedBooks() {
        List<Book> books = bookService.getBannedBooks();
        return Result.success(books);
    }

    @Operation(summary = "Unban a book", description = "Sets the is_banned field of the specified book to 0, is_review to 1, and removes related reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book unbanned successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PutMapping("/unban/{book_id}")
    public Result unbanBook(@PathVariable Integer book_id) {
        try {
            // 1. 解封书籍
            bookService.unbanBook(book_id);
            
            // 确保书籍已被审核 (is_review = 1)
            bookService.setReviewed(book_id);
            
            // 2. 从report表中删除相关举报数据
            reportService.deleteReportByContentTypeAndId("book", book_id);
            
            return Result.success("Book unbanned successfully and related reports deleted");
        } catch (Exception e) {
            return Result.error("Failed to unban book: " + e.getMessage());
        }
    }
}