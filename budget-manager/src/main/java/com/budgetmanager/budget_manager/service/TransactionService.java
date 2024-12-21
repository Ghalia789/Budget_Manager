package com.budgetmanager.budget_manager.service;

import com.budgetmanager.budget_manager.model.SavingsGoal;
import com.budgetmanager.budget_manager.model.Transaction;
import com.budgetmanager.budget_manager.model.TransactionType;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private EmailService emailService;

    // Add a new transaction
    public Transaction addTransaction(Transaction transaction) {
        Transaction savedTransaction = transactionRepository.save(transaction);

        // If the transaction affects a savings goal
        if (transaction.getType() == TransactionType.INCOME && transaction.getSavingsGoal() != null) {
            SavingsGoal goal = transaction.getSavingsGoal();
            double updatedAmount = goal.getCurrentAmount() + transaction.getAmount();
            goal.setCurrentAmount(updatedAmount);

            if (updatedAmount >= goal.getTargetAmount() * 0.9) {
                // Send an email notification
                emailService.sendSavingsGoalAchievementEmail(
                        transaction.getUser().getEmail(),
                        transaction.getUser().getUsername(),
                        goal.getGoalName(),
                        updatedAmount
                );
            }
        }

        return savedTransaction;
    }

    // Update an existing transaction
    public Transaction updateTransaction(int transactionID, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById((long) transactionID)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Update fields
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setAmount(updatedTransaction.getAmount());
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
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null); // Return null if not found
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
    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserUserId(userId);
    }
    public List<Transaction> getLatestTransactions(User user) {
        // Get the transactions sorted by date in descending order
        List<Transaction> allTransactions = getTransactionsSortedByDate(user);

        // Return the first 3 transactions, or the entire list if less than 3
        return allTransactions.stream()
                .limit(2)
                .collect(Collectors.toList());
    }
}
