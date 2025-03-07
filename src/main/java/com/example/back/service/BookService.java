package com.example.back.service;

import com.example.back.pojo.Book;
import java.util.List;

public interface BookService {
    List<Book> listBooks();

    Book getBookById(Integer bookId);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer bookId);

    List<Book> getBooksByType(String bookType);

    // 新增方法
    List<Book> getBooksByTitle(String title);
    // 新增方法
    List<Book> getBooksBySellerId(String bookSellerId);

}
