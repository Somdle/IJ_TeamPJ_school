package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Student {
    private String id; // 학생 식별자
    private String studentId; // 학생 id
    private String name; // 학생 이름
    private String major; // 전공
    private String grade; // 학년
    private ArrayList<String> lecturesIds = new ArrayList<String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ArrayList<String> getLecturesIds() {
        return lecturesIds;
    }

    public void setLecturesIds(ArrayList<String> lecturesIds) {
        this.lecturesIds = lecturesIds;
    }

    public void addLecturesId(String lecturesId) {
        this.lecturesIds.add(lecturesId);
    }

    public void removeLecturesId(String lecturesId) {
        this.lecturesIds.remove(lecturesId);
    }

    public Student(String id, String studentId, String name, String major, String grade, ArrayList<String> lecturesIds) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.lecturesIds = lecturesIds;
    }

    public Student() {

    }

    // JSON 객체를 매개변수로 받아서 Student 객체를 초기화하는 생성자
    public Student(JSONObject json) {
        this.id = json.optString("_id");
        this.studentId = json.optString("studentId");
        this.name = json.optString("name");
        this.major = json.optString("major");
        this.grade = json.optString("grade");

        JSONArray lecturesArray = json.optJSONArray("lectures");
        if (lecturesArray != null) {
            for (int i = 0; i < lecturesArray.length(); i++) {
                this.lecturesIds.add(lecturesArray.optString(i));
            }
        }
    }

    // Student 객체를 JSON 형식으로 변환하는 메서드
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_id", this.id);
        json.put("studentId", this.studentId);
        json.put("name", this.name);
        json.put("major", this.major);
        json.put("grade", this.grade);

        JSONArray lecturesArray = new JSONArray();
        for (String lectureId : this.lecturesIds) {
            lecturesArray.put(lectureId);
        }
        json.put("lectures", lecturesArray);

        return json;
    }
}