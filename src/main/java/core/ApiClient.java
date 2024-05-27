package core;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;

public class ApiClient {
    static String apiUrl = "http://localhost:3000/api/students?id=";

    public static <URLEncoderException> JSONObject HttpGet(String params_id) {
        try {
            // JWT 토큰 생성
            String token = JwtUtil.generateJwtToken("pcu_project");

            // 문자열 인코딩(http 에러 방지)
            String encoded_params_id = URLEncoder.encode(params_id, "UTF-8");

            // HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + encoded_params_id))
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
                // System.out.println(jsonResponse.toString(4)); // JSON을 예쁘게 출력
                return jsonResponse;
            } else {
                System.out.println("HTTP error code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Http get error: " + e.toString());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(HttpGet("학생 식별자"));
    }
}
