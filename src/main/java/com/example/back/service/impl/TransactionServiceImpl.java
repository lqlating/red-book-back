package com.example.back.service.impl;

import com.example.back.mapper.TransactionMapper;
import com.example.back.mapper.BookMapper;
import com.example.back.pojo.Book;
import com.example.back.pojo.Transaction;
import com.example.back.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    @Transactional
    public Transaction createTransaction(Integer book_id, Integer buyer_id) {
        // 获取书籍信息以确认卖家ID
        Book book = bookMapper.getBookById(book_id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        // 创建交易记录
        Transaction transaction = new Transaction();
        transaction.setBook_id(book_id);
        transaction.setBuyer_id(buyer_id);
        transaction.setSeller_id(Integer.parseInt(book.getBook_seller_id()));
        transaction.setStatus("pending");
        
        // 保存交易记录
        transactionMapper.createTransaction(transaction);
        
        // 获取完整的交易信息（包括关联的书籍信息）
        return transactionMapper.getTransactionById(transaction.getTransaction_id());
    }

    @Override
    public List<Transaction> getBuyerTransactions(Integer buyer_id) {
        return transactionMapper.getBuyerTransactions(buyer_id);
    }

    @Override
    public List<Transaction> getSellerTransactions(Integer seller_id) {
        return transactionMapper.getSellerTransactions(seller_id);
    }

    @Override
    @Transactional
    public Transaction processTransaction(Long transaction_id, String action) {
        Transaction transaction = transactionMapper.getTransactionById(transaction_id);
        if (transaction == null) {
            throw new RuntimeException("Transaction not found");
        }

        transaction.setStatus(action);
        transactionMapper.updateTransaction(transaction);

        if ("accepted".equals(action)) {
            // 如果接受交易，将书籍标记为已售出
            bookMapper.markBooksAsSold(Collections.singletonList(transaction.getBook_id()));
        } else if ("rejected".equals(action)) {
            // 如果拒绝交易，删除交易记录
            transactionMapper.deleteTransaction(transaction_id);
            return null;
        }

        return transactionMapper.getTransactionById(transaction_id);
    }
} 