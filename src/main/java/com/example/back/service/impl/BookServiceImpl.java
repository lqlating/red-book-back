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
    public List<Book> listBooks(Integer page, Integer size) {
        return bookMapper.listBooks((page - 1) * size, size);
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
    public List<Book> getBooksByType(String book_type, Integer page, Integer size) {
        return bookMapper.getBooksByType(book_type, (page - 1) * size, size);
    }

    // 新增方法实现
    @Override
    public List<Book> getBooksBySellerId(String book_seller_id, Integer page, Integer size) {
        return bookMapper.getBooksBySellerId(book_seller_id, (page - 1) * size, size);
    }

    // 新增方法实现
    @Override
    public List<Book> getBooksByTitle(String title, Integer page, Integer size) { // 确保方法签名与接口一致
        List<Book> books = bookMapper.getBooksByTitle(title, (page - 1) * size, size);
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
    public List<Book> getBooksByTitleContaining(String title, Integer page, Integer size) {
        List<Book> books = bookMapper.getBooksByTitleContaining(title, (page - 1) * size, size);
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
        return books;
    }

    // 新增方法：将指定书籍的 is_review 和 is_banned 设置为 1
    @Override
    public void setReviewedAndBanned(Integer book_id) {
        bookMapper.setReviewedAndBanned(book_id);
    }

    @Override
    public List<Book> getUnreviewedBooks(Integer page, Integer size) {
        return bookMapper.getUnreviewedBooks((page - 1) * size, size);
    }

    @Override
    public List<Book> getBannedBooks(Integer page, Integer size) {
        return bookMapper.getBannedBooks((page - 1) * size, size);
    }

    @Override
    public void unbanBook(Integer book_id) {
        bookMapper.unbanBook(book_id);
    }

    @Override
    public void setReviewed(Integer book_id) {
        bookMapper.setReviewed(book_id);
    }
}