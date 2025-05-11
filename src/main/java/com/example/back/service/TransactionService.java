package com.example.back.service;

import com.example.back.pojo.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Integer book_id, Integer buyer_id);
    List<Transaction> getBuyerTransactions(Integer buyer_id);
    List<Transaction> getSellerTransactions(Integer seller_id);
    Transaction processTransaction(Long transaction_id, String action);
} 