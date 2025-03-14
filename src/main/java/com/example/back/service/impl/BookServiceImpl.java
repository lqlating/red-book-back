package com.example.back.service.impl;

import com.example.back.mapper.BookMapper;
import com.example.back.pojo.Book;
import com.example.back.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> listBooks() {
        return bookMapper.listBooks();
    }

    @Override
    public Book getBookById(Integer book_id) {
        return bookMapper.getBookById(book_id);
    }

    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    public void deleteBook(Integer book_id) {
        bookMapper.deleteBook(book_id);
    }

    @Override
    public List<Book> getBooksByType(String book_type) {
        return bookMapper.getBooksByType(book_type);
    }

    // 新增方法实现
    @Override
    public List<Book> getBooksBySellerId(String book_seller_id) {
        return bookMapper.getBooksBySellerId(book_seller_id);
    }

    // 新增方法实现
    @Override
    public List<Book> getBooksByTitle(String title) { // 确保方法签名与接口一致
        List<Book> books = bookMapper.getBooksByTitle(title);
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
        return books;
    }

    // 新增方法实现
    @Override
    public List<Book> getBooksByTitleContaining(String title) {
        List<Book> books = bookMapper.getBooksByTitleContaining(title);
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
        return books;
    }
}