/*
package org.sid.notificationservice.configuration;


import org.sid.notificationservice.model.User;
import org.sid.notificationservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        // Create sample user data using builder pattern
        User user1 = User.builder()
                .username("john_doe")
                .password(passwordEncoder.encode("password123"))
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .build();

        User user2 = User.builder()
                .username("jane_doe")
                .password(passwordEncoder.encode("password456"))
                .email("jane.doe@example.com")
                .phoneNumber("0987654321")
                .build();

        // Save users to the database
        userRepository.save(user1);
        userRepository.save(user2);
    }
}*/
