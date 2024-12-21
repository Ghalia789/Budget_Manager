package com.budgetmanager.budget_manager.controller;


import jakarta.servlet.http.HttpSession;
import com.budgetmanager.budget_manager.model.Budget;
import com.budgetmanager.budget_manager.model.BudgetStatus;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.service.BudgetService;
import com.budgetmanager.budget_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @GetMapping("/budgets")
    public String manageBudgets(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        model.addAttribute("budgets", budgetService.getBudgetsByUserId(user.getUserId()));
        return "manage-budgets-homepage";
    }
    @GetMapping("/budgets/new")
    public String showBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());  // Add an empty Budget object to the model
        return "budget-form";  // This will refer to the Thymeleaf template "budget-form.html"
    }


    @PostMapping("/budgets/save")
    public String saveBudget(@Valid Budget budget, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("budget", budget); // Retain the budget data in the model
                model.addAttribute("error", "Please correct the errors in the form."); // Error message for the user
                return "budget-form"; // Return to the form view
            }

            if (budget.getStatus() == null) {
                budget.setStatus(BudgetStatus.ACTIVE); // Default status
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                model.addAttribute("error", "User not found. Please log in again.");
                return "error";
            }
            budget.setUser(user);
            budgetService.saveBudget(budget);
            return "redirect:/budgets";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An unexpected error occurred while saving the budget. Please try again.");
            return "budget-form"; // Return to the form view with the error message
        }
    }

    // List all budgets
    /*@GetMapping
    public String getAllBudgets(Model model) {
        List<Budget> budgets = budgetService.getAllBudgets(); // Fetch all budgets
        model.addAttribute("budgets"); // Add budgets to the model
        return "budget-list"; // Thymeleaf template for listing budgets
    }*/

    // Show the form to update an existing budget
    @GetMapping("/budgets/edit/{id}")
    public String editBudget(@PathVariable("id") int id, Model model) {
        Budget budget = budgetService.getBudgetById(id); // Fetch the budget by ID
        if (budget == null) {
            return "redirect:/budgets"; // If the budget does not exist, redirect to the budget list
        }
        model.addAttribute("budget", budget); // Add the budget to the model
        return "budget-form"; // Show the form to edit the budget
    }

    // Handle the submission of the updated budget
    @PostMapping("/budgets/update/{id}")
    public String updateBudget(@PathVariable("id") int id,
                               @Valid @ModelAttribute("budget") Budget updatedBudget,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "budget-form"; // If there are validation errors, return to the form
        }

        updatedBudget.setBudgetID(id);
        budgetService.updateBudget(id, updatedBudget); // Update the budget
        return "redirect:/budgets"; // Redirect to the list of budgets
    }

    // Handle deleting a budget
    @GetMapping("/budgets/delete/{id}")
    public String deleteBudget(@PathVariable("id") int id) {
        budgetService.deleteBudget(id); // Delete the budget by ID
        return "redirect:/budgets"; // Redirect to the list of all budgets
    }
}
