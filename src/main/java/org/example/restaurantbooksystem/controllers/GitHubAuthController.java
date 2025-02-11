package org.example.restaurantbooksystem.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/github")
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
public class GitHubAuthController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    // Step 1: Redirect user to GitHub for authentication
    @GetMapping
    public ResponseEntity<?> redirectToGitHub() {
        String githubAuthUrl = "https://github.com/login/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=user:email";
        return ResponseEntity.status(302).header("Location", githubAuthUrl).build();
    }

    // Step 2: GitHub redirects back with a "code", exchange it for an access token
    @GetMapping("/callback")
    public ResponseEntity<?> githubCallback(@RequestParam("code") String code) {
        String accessTokenUrl = "https://github.com/login/oauth/access_token" +
                "?client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code;

        Map<String, String> response = restTemplate.postForObject(accessTokenUrl, null, Map.class);
        String accessToken = response.get("access_token");

        // Step 3: Use access token to fetch user details from GitHub
        String userUrl = "https://api.github.com/user";
        Map<String, Object> userInfo = restTemplate.getForObject(userUrl + "?access_token=" + accessToken, Map.class);

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        String avatarUrl = (String) userInfo.get("avatar_url");

        // TODO: Check if user exists in database; if not, save them

        return ResponseEntity.ok(Map.of(
                "name", name,
                "email", email,
                "avatar", avatarUrl,
                "token", accessToken
        ));
    }
}