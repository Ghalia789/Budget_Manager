package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    @GetMapping("/new")
    public String showBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());  // Add an empty Budget object to the model
        return "budget-form";  // This will refer to the Thymeleaf template "budget-form.html"
    }

    @PostMapping("/save")
    public String saveBudget(@Valid Budget budget, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "budget-form";  // If there are validation errors, return to the form
        }

        budgetService.saveBudget(budget);  // Save the valid budget
        return "redirect:/budgets";  // Redirect to the list of all budgets
    }
    // List all budgets
    @GetMapping
    public String getAllBudgets(Model model) {
        List<Budget> budgets = budgetService.getAllBudgets(); // Fetch all budgets
        model.addAttribute("budgets"); // Add budgets to the model
        return "budget-list"; // Thymeleaf template for listing budgets
    }

    // Show the form to update an existing budget
    @GetMapping("/edit/{id}")
    public String editBudget(@PathVariable("id") int id, Model model) {
        Budget budget = budgetService.getBudgetById(id); // Fetch the budget by ID
        if (budget == null) {
            return "redirect:/budgets"; // If the budget does not exist, redirect to the budget list
        }
        model.addAttribute("budget", budget); // Add the budget to the model
        return "budget-form"; // Show the form to edit the budget
    }

    // Handle the submission of the updated budget
    @PostMapping("/update/{id}")
    public String updateBudget(@PathVariable("id") int id,
                               @Valid @ModelAttribute("budget") Budget updatedBudget,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "budget-form"; // If there are validation errors, return to the form
        }

        updatedBudget.setBudgetID(id); // Ensure the correct ID is set for the updated budget
        budgetService.updateBudget(id, updatedBudget); // Update the budget
        return "redirect:/budgets"; // Redirect to the list of budgets
    }

    // Handle deleting a budget
    @GetMapping("/delete/{id}")
    public String deleteBudget(@PathVariable("id") int id) {
        budgetService.deleteBudget(id); // Delete the budget by ID
        return "redirect:/budgets"; // Redirect to the list of all budgets
    }
}
