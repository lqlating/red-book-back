package com.example.back.mapper;

import com.example.back.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

    // 新增方法：将指定书籍的 is_review 和 is_banned 设置为 1
    @Update("UPDATE book SET is_review = 1, is_banned = 1 WHERE book_id = #{bookId};")
    void setReviewedAndBanned(@Param("bookId") Integer bookId);
}