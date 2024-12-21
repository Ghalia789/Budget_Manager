package com.budgetmanager.budget_manager.controller;

import com.budgetmanager.budget_manager.model.SavingsCategory;
import com.budgetmanager.budget_manager.model.User;
import com.budgetmanager.budget_manager.repository.SavingsCategoryRepository;
import com.budgetmanager.budget_manager.service.SavingsCategoryService;
import com.budgetmanager.budget_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/savings-categories")
public class SavingsCategoryController {
    @Autowired
    private SavingsCategoryService savingsCategoryService;

    @Autowired
    private UserService userService;

    // Show all savings categories
    @GetMapping
    public String listSavingsCategories(Model model) {

        User currentUser = userService.getLoggedInUser(); // Implement method to get the logged-in user
        model.addAttribute("categories", savingsCategoryService.getAllSavingsCategoriesByUserId(currentUser.getUserId()));
        return "savingsCategory";
    }

    // Show the form to create a new category
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("savingsCategory", new SavingsCategory());
        return "add-savings-category";
    }

    // Handle saving a new category
    @PostMapping("/add")
    public String saveSavingsCategory(@ModelAttribute("savingsCategory") SavingsCategory savingsCategory) {
        User currentUser = userService.getLoggedInUser(); // Get the logged-in user
        savingsCategory.setUser(currentUser); // Assign the category to the user
        savingsCategoryService.saveSavingsCategory(savingsCategory);
        return "redirect:/savings-categories";
    }

    // Show the form to edit a category
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        SavingsCategory category = savingsCategoryService.getSavingsCategoryById(id);
        if (category == null) {
            throw new RuntimeException("Savings Category not found");
        }
        model.addAttribute("savingsCategory", category);
        return "edit-savings-category";
    }

    // Handle updating a category
    @PostMapping("/edit/{id}")
    public String updateSavingsCategory(@PathVariable("id") Long id, @ModelAttribute("savingsCategory") SavingsCategory updatedCategory) {
        SavingsCategory existingCategory = savingsCategoryService.getSavingsCategoryById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Savings Category not found");
        }

        User currentUser = userService.getLoggedInUser();
        //updatedCategory.setCategoryID(id);
        updatedCategory.setUser(currentUser); // Ensure the user remains the same
        savingsCategoryService.saveSavingsCategory(updatedCategory);
        return "redirect:/savings-categories";
    }

    // Handle deleting a category
    @GetMapping("/delete/{id}")
    public String deleteSavingsCategory(@PathVariable("id") Long id) {
        savingsCategoryService.deleteSavingsCategory(id);
        return "redirect:/savings-categories";
    }
}
