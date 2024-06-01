package core;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;

public class ApiClient {
    static String apiUrl = "http://localhost:3000/api";
    static String secret = "pcu_project";

    public ApiClient(String apiUrl, String secret) {
        this.apiUrl = apiUrl;
        this.secret = secret;
    }

    public ApiClient(){

    }

    private static HttpRequest generateRequest(String domain, String params_id, String method, String body) throws Exception {
        // JWT 토큰 생성
        String token = JwtUtil.generateJwtToken(secret);

        // 문자열 인코딩 (http 에러 방지)
        String encoded_params_id = URLEncoder.encode(params_id, "UTF-8").replace("%3D", "=").replace("%26", "&");

        // HttpRequest 생성
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl + "/" + domain + "?" + encoded_params_id))
                .header("Authorization", "Bearer " + token);

        if ("POST".equals(method)) {
            requestBuilder.header("Content-Type", "application/json");
            return requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
        } else if ("PUT".equals(method)) {
            requestBuilder.header("Content-Type", "application/json");
            return requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body)).build();
        } else if ("DELETE".equals(method)) {
            return requestBuilder.DELETE().build();
        } else {
            return requestBuilder.GET().build();
        }
    }

    public static JSONObject HttpGet(String domain, String params_id) {
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(generateRequest(domain, params_id, "GET", null), HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                return new JSONObject(response.body());
            } else {
                System.out.println("HTTP error code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Http get error: " + e.toString());
            return null;
        }
    }

    public static JSONObject HttpPost(String domain, String params_id, String body) {
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(generateRequest(domain, params_id, "POST", body), HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                return new JSONObject(response.body());
            } else {
                System.out.println("HTTP error code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Http POST error: " + e.toString());
            return null;
        }
    }

    public static JSONObject HttpPut(String domain, String params_id, String body) {
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(generateRequest(domain, params_id, "PUT", body), HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                return new JSONObject(response.body());
            } else {
                System.out.println("HTTP error code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Http POST error: " + e.toString());
            return null;
        }
    }

    public static JSONObject HttpDelete(String domain, String params_id) {
        try {
            // 요청 보내고 응답 받기
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(generateRequest(domain, params_id, "DELETE", null), HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인
            if (response.statusCode() == 200) {
                // 응답 본문을 JSON 객체로 변환
                return new JSONObject(response.body());
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
        switch (1) {
                // 학생
            case 1: // 조회
                System.out.println(HttpGet("students", "id=학생 식별자"));
                break;
            case 2: // 추가
                System.out.println(HttpPost("students", "id=학생 식별자", "{\"studentId\":\"학생 ID\",\"major\":\"전공\",\"grade\":\"학년\",\"name\":\"학생 이름\",\"lectures\":[\"수강 강의 ID 1\",\"수강 강의 ID 2\"],\"_id\":\"학생 식별자\"}"));
                break;
            case 3: // 수정
                System.out.println(HttpPut("students", "id=학생 식별자", "{\"studentId\":\"학생 ID\",\"major\":\"전공 수정\",\"grade\":\"학년 수정\",\"name\":\"학생 이름 수정\",\"lectures\":[\"수강 강의 ID 1 수정\",\"수강 강의 ID 2 수정\"],\"_id\":\"학생 식별자\"}"));
                break;
            case 4: // 제거
                System.out.println(HttpDelete("students", "id=학생 식별자"));
                break;


                // 강의
            case 5: // 조회
                System.out.println(HttpGet("lectures", "id=강의 식별자"));
                break;
            case 6: // 추가
                System.out.println(HttpPost("lectures", "id=강의 식별자", "{\n" +
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
                System.out.println(HttpPut("lectures", "id=강의 식별자", "{\n" +
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
                System.out.println(HttpDelete("lectures", "id=강의 식별자"));
                break;


                // 학생 강의 매칭
            case 9:
                System.out.println(HttpPost("studentsLectures", "studentId=학생 식별자&lectureId=강의 식별자", ""));
                break;
            case 10:
                System.out.println(HttpDelete("studentsLectures", "studentId=학생 식별자&lectureId=강의 식별자"));
                break;

            default:
                System.out.println("Usage: 1 ~ 4");
                break;
        }
    }
}
