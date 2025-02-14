package org.example.restaurantbooksystem.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.restaurantbooksystem.classes.User;
import org.example.restaurantbooksystem.services.UserService;
import org.example.restaurantbooksystem.security.JwtUtil;
import org.example.restaurantbooksystem.security.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData, HttpServletResponse response) {

        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userService.loginUser(email, password);
        if (user != null) {
            // Generate the JWT token
            String token = jwtUtil.generateToken(user);
            // Encrypt the token
            String encryptedToken = encryptionUtil.encrypt(token);

            // Create an HTTP-only cookie to store the encrypted token (expires in 1 hour)
            Cookie cookie = new Cookie("jwt", encryptedToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(3600);
            cookie.setAttribute("SameSite", "Strict");
            cookie.setPath("/");
            // Optionally, in production, set cookie.setSecure(true) and SameSite attribute.
            response.addCookie(cookie);

            // Return a JSON response with user details
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
        }

        Optional<Cookie> jwtCookie = Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findFirst();

        if (jwtCookie.isPresent()) {
            String encryptedToken = jwtCookie.get().getValue();

            if (jwtUtil.validateToken(encryptedToken)) {
                return ResponseEntity.ok(Map.of("message", "Token is valid"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Διαγραφή JWT cookie
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "Strict");
        cookie.setMaxAge(0); // Διαγραφή του cookie
        response.addCookie(cookie);

        return ResponseEntity.ok().body("Logged out successfully");
    }
}