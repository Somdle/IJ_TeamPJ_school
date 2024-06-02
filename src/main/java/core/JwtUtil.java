package core;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    public static String generateJwtToken(String secretKey) {
        return generateJwtToken(secretKey, true, "defaultUser");
    }

    public static String generateJwtToken(String secretKey, boolean useBase64Encoding) {
        return generateJwtToken(secretKey, useBase64Encoding, "defaultUser");
    }

    public static String generateJwtToken(String secretKey, boolean useBase64Encoding, String subjectIdentifier) {
        // Convert the secret key to bytes
        byte[] secretKeyBytes = useBase64Encoding ? java.util.Base64.getEncoder().encode(secretKey.getBytes()) : secretKey.getBytes();
        String secretKeyString = new String(secretKeyBytes);

        // Token expiration time (e.g., 1 hour from now)
        Instant tokenExpirationTime = Instant.now().plus(Duration.ofHours(1));

        // Create a JWT token
        return Jwts.builder()
                .setSubject(subjectIdentifier) // Set the subject (user ID or any identifier)
                .setExpiration(Date.from(tokenExpirationTime)) // Set token expiration time
                .signWith(SignatureAlgorithm.HS256, secretKeyString) // Sign with HS256 algorithm and secret key
                .compact();
    }

    public static void main(String[] args) {
        String token = generateJwtToken("pcu_project");
        System.out.println("Generated JWT token: " + token);

        ApiClient apiClient = new ApiClient("http://localhost:3000/api", "pcu_project");
        System.out.println(apiClient.httpGet("students", "id=학생 식별자"));
    }
}
