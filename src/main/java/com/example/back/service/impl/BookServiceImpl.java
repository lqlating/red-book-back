package com.example.back.service.impl;

import com.example.back.mapper.BookMapper;
import com.example.back.pojo.Book;
import com.example.back.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String BOOK_CACHE_KEY_PREFIX = "book:";
    private static final String BOOK_LIST_CACHE_KEY_PREFIX = "book:list:";
    private static final long CACHE_EXPIRE_TIME = 30; // 缓存过期时间（分钟）
    
    // 预热热门书籍数据，提高高并发性能
    @PostConstruct
    public void preloadHotData() {
        CompletableFuture.runAsync(() -> {
            try {
                // 预热全部书籍列表
                listBooks();
                Thread.sleep(500);
                
                // 异步加载热门分类的书籍列表到缓存
                for (String type : Arrays.asList("Romance", "Science Fiction", "Suspense", "Thriller")) {
                    getBooksByType(type);
                    Thread.sleep(500); // 避免同时加载太多数据
                }
            } catch (Exception e) {
                // 异常处理，预热失败不影响系统正常运行
            }
        });
    }

    @Override
    @Cacheable(value = "bookList", key = "'all'")
    public List<Book> listBooks() {
        return bookMapper.listBooks();
    }

    @Override
    @Cacheable(value = "book", key = "#book_id")
    public Book getBookById(Integer book_id) {
        return bookMapper.getBookById(book_id);
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void deleteBook(Integer book_id) {
        bookMapper.deleteBook(book_id);
    }

    @Override
    @Cacheable(value = "bookList", key = "'type_' + #book_type")
    public List<Book> getBooksByType(String book_type) {
        return bookMapper.getBooksByType(book_type);
    }

    @Override
    @Cacheable(value = "bookList", key = "'seller_' + #book_seller_id")
    public List<Book> getBooksBySellerId(String book_seller_id) {
        return bookMapper.getBooksBySellerId(book_seller_id);
    }

    @Override
    @Cacheable(value = "bookSearch", key = "'title_' + #title")
    public List<Book> getBooksByTitle(String title) {
        List<Book> books = bookMapper.getBooksByTitle(title);
        processBookImages(books);
        return books;
    }

    @Override
    @Cacheable(value = "bookSearch", key = "'titleContaining_' + #title")
    public List<Book> getBooksByTitleContaining(String title) {
        List<Book> books = bookMapper.getBooksByTitleContaining(title);
        processBookImages(books);
        return books;
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void setReviewedAndBanned(Integer book_id) {
        bookMapper.setReviewedAndBanned(book_id);
    }

    @Override
    @Cacheable(value = "bookList", key = "'unreviewed'")
    public List<Book> getUnreviewedBooks() {
        List<Book> books = bookMapper.getUnreviewedBooks();
        processBookImages(books);
        return books;
    }

    @Override
    @Cacheable(value = "bookList", key = "'banned'")
    public List<Book> getBannedBooks() {
        List<Book> books = bookMapper.getBannedBooks();
        processBookImages(books);
        return books;
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void unbanBook(Integer book_id) {
        bookMapper.unbanBook(book_id);
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void setReviewed(Integer book_id) {
        bookMapper.setReviewed(book_id);
    }

    @Override
    @CacheEvict(value = {"book", "bookList"}, allEntries = true)
    public void markBooksAsSold(List<Integer> bookIds) {
        if (bookIds != null && !bookIds.isEmpty()) {
            bookMapper.markBooksAsSold(bookIds);
        }
    }

    private void processBookImages(List<Book> books) {
        for (Book book : books) {
            if (book.getBook_img() != null) {
                String book_img_base64 = Base64.getEncoder().encodeToString(book.getBook_img());
                book.setBook_img_base64(book_img_base64);
            }
        }
    }
}