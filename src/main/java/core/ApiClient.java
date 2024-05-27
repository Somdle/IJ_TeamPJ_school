package core;

import org.json.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.net.URLEncoder;

public class ApiClient {

    public static void main(String[] args) {
        try {
            // JWT 토큰 생성
            String token = JwtUtil.generateJwtToken();

            String originalString = "학생 식별자";
            String encodedString = URLEncoder.encode(originalString, "UTF-8");
            // API URL
            String apiUrl = "http://localhost:3000/api/students?id=" + encodedString;

            // HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                JSONObject jsonResponse = new JSONObject(response.body());

                // JSON 데이터 출력
                System.out.println(jsonResponse.toString(4)); // JSON을 예쁘게 출력
            } else {
                System.out.println("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("ERR");
            e.printStackTrace();
        }
    }
}
