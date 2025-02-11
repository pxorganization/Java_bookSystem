package org.example.restaurantbooksystem.services;

import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    // Registration: Check for duplicates, hash password and save the user
    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        // Hash the password before saving
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    // Login: Validate email and password; return the user if correct
    public User loginUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
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
