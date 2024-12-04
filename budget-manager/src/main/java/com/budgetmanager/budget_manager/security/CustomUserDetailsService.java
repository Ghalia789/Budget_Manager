package com.budgetmanager.budget_manager.security;

import com.budgetmanager.budget_manager.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Log the username being fetched
        System.out.println("Attempting to load user by username: " + username);

        // Fetch the user from the database once
        Optional<com.budgetmanager.budget_manager.model.User> userObj = userRepository.findByUsername(username);

        // If no user is found, log the error and throw exception
        if (userObj.isEmpty()) {
            System.out.println("User not found for: " + username);
            throw new UsernameNotFoundException("User not found");
        }

        // Assuming userObj is an object with getUsername() and getPassword() methods
        String user = userObj.get().getUsername(); // Or, access the appropriate field directly
        String password = userObj.get().getPassword(); // Similarly, access the password field directly

        // Log the user and password for debugging purposes
        System.out.println("User found: " + user);
        System.out.println("Password found: " + password);

        // Return a Spring Security User object
        return User.builder()
                .username(user)
                .password(password) // Ensure the password is encoded in the database
                .roles("USER") // Assuming roles are stored as a list of strings
                .build();
    }
}
