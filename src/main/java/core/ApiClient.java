package core;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    public static void main(String[] args) {
        String studentId = "학생 식별자";
        String url = "http://localhost:3000/api/students?id=" + studentId;
        String bearerToken = "pcu_project";

        try {
            // HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // HttpRequest 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + bearerToken)
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
            e.printStackTrace();
        }
    }
}
