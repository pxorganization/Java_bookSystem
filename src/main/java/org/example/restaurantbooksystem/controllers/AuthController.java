package org.example.restaurantbooksystem.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.services.UserService;
import org.example.restaurantbooksystem.utils.JwtUtil;
import org.example.restaurantbooksystem.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final EncryptionUtil encryptionUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, EncryptionUtil encryptionUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.encryptionUtil = encryptionUtil;
    }

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User createdUser = userService.registerUser(user);
            // Do not return the password in the response
            createdUser.setPassword(null);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user");
        }
    }

    // Login endpoint that returns a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData, HttpServletResponse response) throws IOException {

        String email = loginData.get("email");
        String password = loginData.get("password");
        User user = userService.loginUser(email, password);

        if (user != null) {

            String token = jwtUtil.generateToken(user);
            System.out.println(token);
            String encryptedToken = encryptionUtil.encrypt(token);

            // Create an HTTP-only cookie to store the encrypted token (expires in 1 hour)
            Cookie cookie = new Cookie("jwtkey", encryptedToken);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);

            // Return JSON response with user details (without the token)
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}