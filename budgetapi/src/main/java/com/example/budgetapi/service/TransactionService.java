package com.example.budgetapi.service;

import com.example.budgetapi.repository.TransactionRepository;
import com.example.budgetapi.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id){
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getUserTransactions(Long userId){
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getUserTransactionsByType(Long userId, String type){
        return transactionRepository.findByUserIdAndType(userId,type);
    }

    public List<Transaction> getUserTransactionsByDateRange(Long userId, LocalDate start, LocalDate end){
        return transactionRepository.findByUserIdAndDateBetween(userId,start,end);
    }

    public double getMonthlyBalance(Long userId, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Transaction> transactions = transactionRepository.findByUserIdAndDateBetween(userId, start, end);

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        return totalIncome - totalExpense;
    }

}
