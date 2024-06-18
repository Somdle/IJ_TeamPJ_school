package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id; // 학생 식별자
    private String studentId; // 학생 id
    private String name; // 학생 이름
    private String major; // 전공
    private String grade; // 학년
    private List<String> lecturesIds; // 수강 강의 ID 리스트(재영)
    private Timetable timetable; // 시간표(재영)

    public Student(String id, String studentId, String name, String major, String grade, List<String> lecturesIds) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.lecturesIds = lecturesIds;
        this.timetable = new Timetable(); // 빈 시간표 초기화(재영)
    }

    // JSON 객체로부터 초기화하는 생성자
    public Student(JSONObject json) {
        this.id = json.optString("_id");
        this.studentId = json.optString("studentId");
        this.name = json.optString("name");
        this.major = json.optString("major");
        this.grade = json.optString("grade");

        // 빈 시간표 초기화
        this.timetable = new Timetable();

        // 수강 강의 ID 초기화
        JSONArray lecturesArray = json.optJSONArray("lectures");
        if (lecturesArray != null) {
            this.lecturesIds = new ArrayList<>();
            for (int i = 0; i < lecturesArray.length(); i++) {
                this.lecturesIds.add(lecturesArray.optString(i));
            }
        }
    }

    // JSON 객체로 변환
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

    // Getters와 Setters
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

    public List<String> getLecturesIds() {
        return lecturesIds;
    }

    public void setLecturesIds(List<String> lecturesIds) {
        this.lecturesIds = lecturesIds;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    // 시간표 관리를 위한 추가 메서드들
    public void addToTimetable(Lecture lecture) {
        this.timetable.addLecture(lecture);
    }

    public void removeFromTimetable(Lecture lecture) {
        this.timetable.removeLecture(lecture);
    }
}
