package core;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Scanner;

public class App {
    public static int printMenu(){
        System.out.println("메뉴");
        System.out.println("0. 종료");
        System.out.println("1. 강의 추가");
        System.out.println("2. 강의 제거");
        System.out.println("메뉴를 선택하세요: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static void main(String[] args) {
        // 받아온 정보를 처리할 객체
        Student student;
        Lecture lecture;

        while(true){
            // 중간에 나가는 옵션
            boolean exitFlag = false;

            // 강의 선택 자료
            JSONObject ans;
            String lectureId;

            switch (printMenu()) {
                case 0:
                    System.exit(0);
                    exitFlag = true;
                    break;
                case 1: // 추가
                    System.out.println("추가할 강의 ID를 입력하세요.");
                    lectureId = new Scanner(System.in).nextLine();
                    ans = ApiClient.HttpPost("studentsLectures", "studentId=학생 식별자&lectureId=" + lectureId, "");
                    System.out.println("DB 처리결과: " + ans);

                    student = new Student(ApiClient.HttpGet("students", "id=학생 식별자"));
                    lecture = new Lecture(ApiClient.HttpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                case 2: // 제거
                    System.out.println("제거할 강의 ID를 입력하세요.");
                    lectureId = new Scanner(System.in).nextLine();
                    ans = ApiClient.HttpDelete("studentsLectures", "studentId=학생 식별자&lectureId=" + lectureId);
                    System.out.println("DB 처리결과: " + ans);

                    student = new Student(ApiClient.HttpGet("students", "id=학생 식별자"));
                    lecture = new Lecture(ApiClient.HttpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                default:
                    System.out.println("Usage: 0 ~ 2");
                    break;
            }

            if(exitFlag){
                System.out.println("종료");
                break;
            }
        }
    }
}
