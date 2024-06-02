package core;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private final String apiUrl;
    private final String secret;

    public ApiClient(String apiUrl, String secret) {
        this.apiUrl = apiUrl;
        this.secret = secret;
    }

    private enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    private HttpRequest generateRequest(String domain, String paramsId, HttpMethod method, String body){
        // JWT 토큰 생성
        String token = JwtUtil.generateJwtToken(secret);

        // 문자열 인코딩 (http 에러 방지)
        String encodedParamsId = URLEncoder.encode(paramsId, StandardCharsets.UTF_8);
        encodedParamsId = encodedParamsId.replace("%3D", "=");
        encodedParamsId = encodedParamsId.replace("%26", "&");

        // HttpRequest 생성
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/" + domain + "?" + encodedParamsId))
                .header("Authorization", "Bearer " + token);

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            requestBuilder.header("Content-Type", "application/json");
            return requestBuilder.method(method.name(), HttpRequest.BodyPublishers.ofString(body)).build();
        } else {
            return requestBuilder.method(method.name(), HttpRequest.BodyPublishers.noBody()).build();
        }
    }

    private JSONObject httpRequest(String domain, String paramsId, HttpMethod method, String body) {
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(generateRequest(domain, paramsId, method, body), HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                return new JSONObject(response.body());
            } else {
                throw new RuntimeException("HTTP error code: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Http " + method.name() + " error: " + e.toString());
        }
    }

    public JSONObject httpGet(String domain, String paramsId) {
        return httpRequest(domain, paramsId, HttpMethod.GET, null);
    }

    public JSONObject httpPost(String domain, String paramsId, String body) {
        return httpRequest(domain, paramsId, HttpMethod.POST, body);
    }

    public JSONObject httpPut(String domain, String paramsId, String body) {
        return httpRequest(domain, paramsId, HttpMethod.PUT, body);
    }

    public JSONObject httpDelete(String domain, String paramsId) {
        return httpRequest(domain, paramsId, HttpMethod.DELETE, null);
    }

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient("http://localhost:3000/api", "pcu_project");

        switch (1) {
                // 학생
            case 1: // 조회
                System.out.println(apiClient.httpGet("students", "id=학생 식별자"));
                break;
            case 2: // 추가
                System.out.println(apiClient.httpPost("students", "id=학생 식별자", "{\"studentId\":\"학생 ID\",\"major\":\"전공\",\"grade\":\"학년\",\"name\":\"학생 이름\",\"lectures\":[\"수강 강의 ID 1\",\"수강 강의 ID 2\"],\"_id\":\"학생 식별자\"}"));
                break;
            case 3: // 수정
                System.out.println(apiClient.httpPut("students", "id=학생 식별자", "{\"studentId\":\"학생 ID\",\"major\":\"전공 수정\",\"grade\":\"학년 수정\",\"name\":\"학생 이름 수정\",\"lectures\":[\"수강 강의 ID 1 수정\",\"수강 강의 ID 2 수정\"],\"_id\":\"학생 식별자\"}"));
                break;
            case 4: // 제거
                System.out.println(apiClient.httpDelete("students", "id=학생 식별자"));
                break;


                // 강의
            case 5: // 조회
                System.out.println(apiClient.httpGet("lectures", "id=강의 식별자"));
                break;
            case 6: // 추가
                System.out.println(apiClient.httpPost("lectures", "id=강의 식별자", "{\n" +
                        "    \"_id\": \"강의 식별자\",\n" +
                        "    \"lectureId\": \"강의 ID\",\n" +
                        "    \"name\": \"강의명\",\n" +
                        "    \"professor\": \"담당교수\",\n" +
                        "    \"classRoom\": \"강의실 위치\",\n" +
                        "    \"date\": \"강의 날짜\",\n" +
                        "    \"startTime\": \"강의 시작 시간 (교시 단위)\",\n" +
                        "    \"endTime\": \"강의 종료 시간 (교시 단위)\",\n" +
                        "    \"students\": [\n" +
                        "      \"수강 학생 ID\",\n" +
                        "      \"수강 학생 ID\"\n" +
                        "    ]\n" +
                        "}"));
                break;
            case 7:
                System.out.println(apiClient.httpPut("lectures", "id=강의 식별자", "{\n" +
                        "    \"_id\": \"강의 식별자\",\n" +
                        "    \"lectureId\": \"강의 ID\",\n" +
                        "    \"name\": \"강의명\",\n" +
                        "    \"professor\": \"홍길동\",\n" +
                        "    \"classRoom\": \"강의실 위치\",\n" +
                        "    \"date\": \"강의 날짜\",\n" +
                        "    \"startTime\": \"강의 시작 시간 (교시 단위)\",\n" +
                        "    \"endTime\": \"강의 종료 시간 (교시 단위)\",\n" +
                        "    \"students\": [\n" +
                        "      \"수강 학생 ID\",\n" +
                        "      \"수강 학생 ID\"\n" +
                        "    ]\n" +
                        "}"));
                break;
            case 8:
                System.out.println(apiClient.httpDelete("lectures", "id=강의 식별자"));
                break;


                // 학생 강의 매칭
            case 9:
                System.out.println(apiClient.httpPost("studentsLectures", "studentId=학생 식별자&lectureId=강의 식별자", ""));
                break;
            case 10:
                System.out.println(apiClient.httpDelete("studentsLectures", "studentId=학생 식별자&lectureId=강의 식별자"));
                break;

            default:
                System.out.println("Usage: 1 ~ 4");
                break;
        }
    }
}
