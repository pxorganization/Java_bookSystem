package org.example.restaurantbooksystem.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.restaurantbooksystem.classes.Reservation;
import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.repositories.UserRepository;
import org.example.restaurantbooksystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    // Register
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

    // Login
    public User loginUser(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null && bCryptPasswordEncoder.matches(rawPassword, user.getPassword())) {
            user.setPassword(null);
            return user;
        }
        return null;
    }

    // All Users
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }

    //Update User By Id
    public void updateUserById(UUID id, User updatedUser, HttpServletRequest request) {
        if( jwtUtil.isAuthenticated(request)){
            userRepository.save(updatedUser);
        }
    }

    // Get User By Id
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
}