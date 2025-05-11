package com.example.back.controller;

import com.example.back.pojo.Result;
import com.example.back.pojo.Transaction;
import com.example.back.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Create a new transaction request", description = "Creates a new transaction request for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction request created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @PostMapping("/create")
    public Result createTransaction(@RequestParam Integer book_id, @RequestParam Integer buyer_id) {
        try {
            Transaction transaction = transactionService.createTransaction(book_id, buyer_id);
            return Result.success(transaction);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "Get user's buying transactions", description = "Returns all transactions where the user is the buyer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @GetMapping("/buyer/{buyer_id}")
    public Result getBuyerTransactions(@PathVariable Integer buyer_id) {
        try {
            List<Transaction> transactions = transactionService.getBuyerTransactions(buyer_id);
            if (transactions.isEmpty()) {
                return Result.error("No buying transactions found");
            }
            return Result.success(transactions);
        } catch (Exception e) {
            return Result.error("Failed to get buying transactions: " + e.getMessage());
        }
    }

    @Operation(summary = "Get user's selling transactions", description = "Returns all transactions where the user is the seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @GetMapping("/seller/{seller_id}")
    public Result getSellerTransactions(@PathVariable Integer seller_id) {
        try {
            List<Transaction> transactions = transactionService.getSellerTransactions(seller_id);
            if (transactions.isEmpty()) {
                return Result.error("No selling transactions found");
            }
            return Result.success(transactions);
        } catch (Exception e) {
            return Result.error("Failed to get selling transactions: " + e.getMessage());
        }
    }

    @Operation(summary = "Process transaction", description = "Accept or reject a transaction request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction processed successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class)) })
    })
    @PutMapping("/process/{transaction_id}")
    public Result processTransaction(
            @PathVariable Long transaction_id,
            @RequestParam String action) {
        try {
            if (!action.equals("accepted") && !action.equals("rejected")) {
                return Result.error("Invalid action. Must be 'accepted' or 'rejected'");
            }
            Transaction transaction = transactionService.processTransaction(transaction_id, action);
            if (transaction == null && "rejected".equals(action)) {
                return Result.success("Transaction rejected and deleted successfully");
            }
            return Result.success(transaction);
        } catch (RuntimeException e) {
            return Result.error("Failed to process transaction: " + e.getMessage());
        }
    }
} 