package com.budgetmanager.budget_manager;

import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.repository.BudgetRepository;
import com.budgetmanager.budget_manager.service.BudgetService;
import com.budgetmanager.budget_manager.service.errors.BudgetNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class BudgetServiceTest {
    @Mock
    private BudgetRepository budgetRepository;

    @InjectMocks
    private BudgetService budgetService;

    private Budget budget;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        budget = new Budget();
        budget.setBudgetID(1);
        budget.setAmount(1000);
        budget.setStartDate(LocalDate.of(2024, 12, 1));
        budget.setEndDate(LocalDate.of(2024, 12, 31));
    }

    @Test
    void testSaveBudget() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);

        Budget savedBudget = budgetService.saveBudget(budget);

        assertNotNull(savedBudget);
        assertEquals(budget.getBudgetID(), savedBudget.getBudgetID());
        verify(budgetRepository, times(1)).save(budget);
    }

    @Test
    void testGetAllBudgets() {
        when(budgetRepository.findAll()).thenReturn(Arrays.asList(budget));

        List<Budget> budgets = budgetService.getAllBudgets();

        assertFalse(budgets.isEmpty());
        assertEquals(1, budgets.size());
        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    void testGetBudgetById() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(budget));

        Budget foundBudget = budgetService.getBudgetById(1);

        assertNotNull(foundBudget);
        assertEquals(budget.getBudgetID(), foundBudget.getBudgetID());
        verify(budgetRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBudget() {
        // Arrange
        Budget existingBudget = new Budget();
        existingBudget.setBudgetID(1);
        existingBudget.setAmount(1000); // Original amount

        Budget updatedBudget = new Budget();
        updatedBudget.setBudgetID(1);
        updatedBudget.setAmount(1500); // Amount to be updated

        when(budgetRepository.existsById(1L)).thenReturn(true);
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(existingBudget)); // Mocking the retrieval of existing budget
        when(budgetRepository.save(any(Budget.class))).thenReturn(updatedBudget); // Return the updated budget

        // Act
        Budget result = budgetService.updateBudget(1, updatedBudget);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getBudgetID());
        assertEquals(1500, result.getAmount()); // Check if the amount has been updated
        verify(budgetRepository, times(1)).save(existingBudget); // Verify that the save method was called once
    }

    @Test
    void testDeleteBudget() {
        when(budgetRepository.existsById(1L)).thenReturn(true);

        budgetService.deleteBudget(1);

        verify(budgetRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBudgetNotFound() {
        when(budgetRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(BudgetNotFoundException.class, () -> {
            budgetService.deleteBudget(1);
        });

        assertEquals("Budget not found", exception.getMessage());
    }
}
