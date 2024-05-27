package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lecture {
    private String id; // 강의 식별자
    private String lectureId; // 강의 id
    private String name; // 강의명
    private String professor; // 담당교수
    private String classRoom; // 위치
    private String date; // 강의날짜
    private String startTime; // 강의시간 (교시단위)
    private String endTime; // 강의시간 (교시단위)
    private ArrayList<String> studentIds = new ArrayList<String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getStudents() {
        return studentIds;
    }

    public void setStudents(ArrayList<String> studentIds) {
        this.studentIds = studentIds;
    }

    public void addStudent(String studentId) {
        this.studentIds.add(studentId);
    }

    public void removeStudent(String studentId) {
        this.studentIds.remove(studentId);
    }

    public Lecture(String id, String lectureId, String name, String professor, String classRoom, String date, String startTime, String endTime, ArrayList<String> studentIds) {
        this.id = id;
        this.lectureId = lectureId;
        this.name = name;
        this.professor = professor;
        this.classRoom = classRoom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentIds = studentIds;
    }

    // JSON 객체를 인자로 받는 생성자
    public Lecture(JSONObject json) {
        this.id = json.optString("_id");
        this.lectureId = json.optString("lectureId");
        this.name = json.optString("name");
        this.professor = json.optString("professor");
        this.classRoom = json.optString("classRoom");
        this.date = json.optString("date");
        this.startTime = json.optString("startTime");
        this.endTime = json.optString("endTime");

        // students 배열 처리
        JSONArray studentsJsonArray = json.optJSONArray("students");
        if (studentsJsonArray != null) {
            this.studentIds = new ArrayList<>();
            for (int i = 0; i < studentsJsonArray.length(); i++) {
                this.studentIds.add(studentsJsonArray.optString(i));
            }
        }
    }

    public Lecture() {

    }

    // JSON 변환 메서드 추가
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_id", this.id);
        json.put("lectureId", this.lectureId);
        json.put("name", this.name);
        json.put("professor", this.professor);
        json.put("classRoom", this.classRoom);
        json.put("date", this.date);
        json.put("startTime", this.startTime);
        json.put("endTime", this.endTime);

        JSONArray studentsArray = new JSONArray();
        for (String studentId : this.studentIds) {
            studentsArray.put(studentId);
        }
        json.put("students", studentsArray);

        return json;
    }
}
