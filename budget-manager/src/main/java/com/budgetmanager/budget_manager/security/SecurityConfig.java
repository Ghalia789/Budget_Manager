package com.budgetmanager.budget_manager.security;

import com.budgetmanager.budget_manager.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);  // Logger instance

    private final CustomUserDetailsService userDetailsService;

    // Inject CustomUserDetailsService
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        logger.info("SecurityConfig initialized with CustomUserDetailsService");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");

        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/", "/signup", "/css/**", "/js/**").permitAll()  // Allow unauthenticated access
                .anyRequest().authenticated()  // Require authentication for other paths
                .and()
                .formLogin() // Use Spring Security's default form login
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/dashboard", true) // Redirect to /dashboard after successful login
                .permitAll()
                .permitAll() // Allow login page to be accessed by all
                .and()
                .logout()
                .logoutUrl("/logout")  // Define the logout URL
                .logoutSuccessUrl("/login")  // Redirect to login page after successful logout
                .invalidateHttpSession(true)  // Invalidate the session to log out the user
                .clearAuthentication(true);  // Clear authentication info


        logger.info("Security filter chain configured successfully");

        return http.build();  // Return the security configuration
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Using BCryptPasswordEncoder for password encoding");
        return new BCryptPasswordEncoder();  // Use BCrypt for password encoding
    }

    // Use DaoAuthenticationProvider to authenticate against database
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        logger.info("Configuring AuthenticationManager");

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); // Configure to use our CustomUserDetailsService

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        logger.info("AuthenticationManager configured successfully");

        return authenticationManager;
    }
}
