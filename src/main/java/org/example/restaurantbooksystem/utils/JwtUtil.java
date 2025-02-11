package org.example.restaurantbooksystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.restaurantbooksystem.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${secret_jwt}")
    private String JWT_SECRET;

    private final EncryptionUtil encryptionUtil;

    @Autowired
    public JwtUtil(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }

    // Generate a token with a 1-hour expiration time
    public String generateToken(User user) {

        long expirationMillis = 3600 * 1000L;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Μέθοδος που επικυρώνει το JWT token
    public Boolean validateToken(String encryptedToken) {
        try {
            String token = encryptionUtil.decrypt(encryptedToken);  // ✅ Αποκρυπτογράφηση του token
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));

            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            Date expiration = claimsJws.getPayload().getExpiration();
            return expiration.after(new Date());  // ✅ Επιστρέφει true αν το token δεν έχει λήξει
        } catch (Exception e) {
            return false;  // Αν το token δεν είναι έγκυρο ή έχει λήξει
        }
    }


}