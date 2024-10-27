package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.model.TransactionType;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    // Add a new transaction
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    // Update an existing transaction
    public Transaction updateTransaction(int transactionID, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById((long) transactionID)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Update fields
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setType(updatedTransaction.getType());
        transaction.setDescription(updatedTransaction.getDescription());

        return transactionRepository.save(transaction);
    }
    // Delete a transaction
    public void deleteTransaction(int transactionID) {
        if (!transactionRepository.existsById((long) transactionID)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById((long) transactionID);
    }
    public List<Transaction> getAllTransactions(User user) {
        return transactionRepository.findByUser(user);
    }
    //filter or process transactions based on the type
    public List<Transaction> getIncomeTransactions(User user) {
        return transactionRepository.findByUserAndType(user, TransactionType.INCOME);
    }

    public List<Transaction> getExpenseTransactions(User user) {
        return transactionRepository.findByUserAndType(user, TransactionType.EXPENSE);
    }
    // Get transactions by date range
    public List<Transaction> getTransactionsByDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUserAndDateBetween(user, startDate, endDate);
    }
    // Get transactions sorted by date
    public List<Transaction> getTransactionsSortedByDate(User user) {
        return transactionRepository.findByUserOrderByDateDesc(user);
    }
}
