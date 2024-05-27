// API 암호화 통신용 클래스
// JWT Bearer Request Header HS256 Base64 Encoded

package core;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Base64;

public class JwtUtil {

    public static String generateJwtToken(String secret) {
        // Encode the secret key using Base64
        byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes());
        String base64Secret = new String(secretBytes);

        // Token expiration time (e.g., 1 hour from now)
        long expirationMillis = System.currentTimeMillis() + 3600000; // 1 hour

        // Create a JWT token
        return Jwts.builder()
//                .setSubject("user123") // Set the subject (user ID or any identifier)
                .setExpiration(new Date(expirationMillis)) // Set token expiration time
                .signWith(SignatureAlgorithm.HS256, base64Secret) // Sign with HS256 algorithm and secret key
                .compact();
    }

    public static void main(String[] args) {
        String token = generateJwtToken("pcu_project");
        System.out.println("Generated JWT token: " + token);
    }
}
