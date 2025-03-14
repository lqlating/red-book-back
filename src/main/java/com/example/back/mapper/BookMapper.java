package com.example.back.mapper;

import com.example.back.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> listBooks();

    Book getBookById(Integer bookId);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer bookId);

    List<Book> getBooksByType(@Param("book_type") String book_type);

    // 新增方法
    List<Book> getBooksBySellerId(@Param("book_seller_id") String book_seller_id);

    // 新增方法
    List<Book> getBooksByTitle(@Param("title") String title);

    // 新增方法
    List<Book> getBooksByTitleContaining(@Param("title") String title);
}