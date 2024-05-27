package core;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "pcu_project";

    public static String createToken() {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 토큰 만료 시간 설정 (예: 현재로부터 1시간 후)
        long expMillis = nowMillis + 3600000;
        Date exp = new Date(expMillis);

        // JWT 토큰 생성
        String jwt = Jwts.builder()
                .setSubject("user") // 예시로 'user'를 subject로 사용
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }

    public static void main(String[] args) {
        String token = createToken();
        System.out.println("Generated JWT Token: " + token);
    }
}
