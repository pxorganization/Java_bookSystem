package org.example.restaurantbooksystem.services;

import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    // BCryptPasswordEncoder can be injected as a bean as well.
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new user
    public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Optionally, check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Hash the password
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setBirthyear(user.getBirthyear());
        return userRepository.save(user);
    }

    // Login an existing user
    public User loginUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            user.setPassword(null);
            return user;
        }
        return null;
    }

    // Retrieve all users (for demonstration purposes)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve a single user by id
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

}
