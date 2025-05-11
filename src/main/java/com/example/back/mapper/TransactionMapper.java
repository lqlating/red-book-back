package com.example.back.mapper;

import com.example.back.pojo.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransactionMapper {
    void createTransaction(Transaction transaction);
    Transaction getTransactionById(@Param("transaction_id") Long transaction_id);
    List<Transaction> getBuyerTransactions(@Param("buyer_id") Integer buyer_id);
    List<Transaction> getSellerTransactions(@Param("seller_id") Integer seller_id);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(@Param("transaction_id") Long transaction_id);
} 