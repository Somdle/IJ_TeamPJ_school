// 추가한 코드(재영)
// 학생의 시간표를 나타내는 'Timetable' 클래스 정의. 학생들의 수업 일정을 관리하는 'Lecture' 객체들의 컬렉션을 포함.

package core;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Lecture> lectures;

    public Timetable() {
        this.lectures = new ArrayList<>();
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
    }
}
