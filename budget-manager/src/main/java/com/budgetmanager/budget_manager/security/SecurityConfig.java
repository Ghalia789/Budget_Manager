package com.budgetmanager.budget_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @SuppressWarnings("deprecation")  // Suppress deprecation warnings

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/", "/signup", "/css/**", "/js/**").permitAll()  // Allow unauthenticated access
                .anyRequest().authenticated()  // Require authentication for other paths
                .and()
                .formLogin() //deprecated but eeh
                .loginPage("/login") // Define custom login page URL
                .successForwardUrl("/dashboard") //Define page URL after successful login
                //.failureForwardUrl()
                .permitAll()  // Allow access to login page for all users
                .and()
                .logout()
                .permitAll();  // Allow users to log out

        return http.build();  // Build and return the security configuration
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Create and register the BCryptPasswordEncoder bean
    }


}
