package com.example.back.service;

import com.example.back.pojo.Book;
import java.util.List;

public interface BookService {
    List<Book> listBooks();

    Book getBookById(Integer bookId);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer bookId);
}
