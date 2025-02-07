package org.example.restaurantbooksystem.controllers;

import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        User loggedUser = userService.loginUser(email, password);
        if (loggedUser != null) {
            // Do not return the password in the response.
            loggedUser.setPassword(null);
            return ResponseEntity.ok(loggedUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User createdUser = userService.registerUser(user);
            // Do not return the password in the response.
            createdUser.setPassword(null);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user");
        }
    }

}
