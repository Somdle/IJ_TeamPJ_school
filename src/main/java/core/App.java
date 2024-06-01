package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // 학생 정보 받아오기
        Student student = new Student(ApiClient.HttpGet("students", "학생 식별자"));

        // 강의 정보 받아오기
        Lecture lecture = new Lecture(ApiClient.HttpGet("lectures", "강의 식별자"));


    }
}
