package com.example.back.service;

import com.example.back.pojo.Book;
import java.util.List;

public interface BookService {
    List<Book> listBooks();

    Object getBookById(Integer id);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer bookId);

    List<Book> getBooksByType(String bookType);

    // 新增方法
    List<Book> getBooksByTitle(String title);
    // 新增方法
    List<Book> getBooksBySellerId(String bookSellerId);

    // 新增方法
    List<Book> getBooksByTitleContaining(String title);

    // 新增方法：将指定书籍的 is_review 和 is_banned 设置为 1
    void setReviewedAndBanned(Integer book_id);

    // 新增方法：获取所有待审核的书籍
    List<Book> getUnreviewedBooks();

    // 新增方法：获取所有被禁止的书籍
    List<Book> getBannedBooks();

    // 新增方法：解禁书籍
    void unbanBook(Integer book_id);

    // 新增方法：将指定书籍的 is_review 设置为 1（标记为已审核）
    void setReviewed(Integer book_id);
}