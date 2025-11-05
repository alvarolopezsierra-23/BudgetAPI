package com.example.budgetapi.controller;

import com.example.budgetapi.model.Transaction;
import com.example.budgetapi.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public void addTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionService.getUserTransactions(userId);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public List<Transaction> getUserTransactionsByType(@PathVariable Long userId, @PathVariable String type) {
        return transactionService.getUserTransactionsByType(userId, type);
    }

    @GetMapping("/user/{userId}/balance/{month}/{year}")
    public double getMonthlyBalance(@PathVariable Long userId, @PathVariable int month, @PathVariable int year) {
        return transactionService.getMonthlyBalance(userId, month, year);
    }
}
