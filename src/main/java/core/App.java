package core;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static int printMenu(){
        System.out.println("메뉴"); // 규석의 새 주석
        System.out.println("0. 종료");
        System.out.println("1. 강의 추가");
        System.out.println("2. 강의 제거");
        System.out.println("3. 강의 내용 추가");
        System.out.println("4. 강의 내용 제거");
        System.out.println("5. 전체 학생 조회");
        System.out.println("6. 전체 강의 조회");
        System.out.println("7. 학생 시간표 조회"); // 추가한 코드(재영)
        System.out.println("8. 학생 시간표 수정"); // 추가한 코드(재영)
        System.out.println("메뉴를 선택하세요: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static void main(String[] args) throws JSONException {
        // api객체
        ApiClient apiClient = new ApiClient("http://somdlesupport.iptime.org:3003/api", "pcu_project");

        // 타겟 학생
        Student targetStudent = new Student(apiClient.httpGet("students", "id=학생 식별자"));

        // 시간표 사용 예시
        targetStudent.addToTimetable(new Lecture("1", "lecture1", "웹프로그래밍", "임선영 교수", "A 강의실", "2024-06-20", "9", "11", new ArrayList<>()));

        // API결과 확인용 변수
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
                    ans = apiClient.httpPost("studentsLectures", "studentId=" + targetStudent.getId() + "&lectureId=" + lectureId, "");
                    System.out.println("DB 처리결과: " + ans);

                    student = new Student(apiClient.httpGet("students", "id=" + targetStudent.getId()));
                    lecture = new Lecture(apiClient.httpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                case 2: // 제거
                    System.out.println("제거할 강의 ID를 입력하세요.");
                    lectureId = new Scanner(System.in).nextLine();
                    ans = apiClient.httpDelete("studentsLectures", "studentId=" + targetStudent.getId() + "&lectureId=" + lectureId);
                    System.out.println("DB 처리결과: " + ans);

                    student = new Student(apiClient.httpGet("students", "id=" + targetStudent.getId()));
                    lecture = new Lecture(apiClient.httpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                case 3: // 강의 추가
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("id, 강의식별자: ");
                    String temp_id = scanner.next();
                    System.out.print("lectureId, 강의 id: ");
                    String temp_lectureId = scanner.next();
                    System.out.print("name, 강의명: ");
                    String temp_name = scanner.next();
                    System.out.print("professor, 담당교수: ");
                    String temp_professor = scanner.next();
                    System.out.print("classRoom, 강의실 위치: ");
                    String temp_classRoom = scanner.next();
                    System.out.print("date, 강의 날짜: ");
                    String temp_date = scanner.next();
                    System.out.print("startTime, 강의 시작 시간 (교시 단위): ");
                    String temp_startTime = scanner.next();
                    System.out.print("endTime, 강의 종료 시간 (교시 단위): ");
                    String temp_endTime = scanner.next();

                    ArrayList<String> temp_studentIds = new ArrayList<>();

                    Lecture temp_Lecture = new Lecture(temp_id, temp_lectureId, temp_name, temp_professor, temp_classRoom, temp_date, temp_startTime, temp_endTime, temp_studentIds);

                    ans = apiClient.httpPost("lectures", "", temp_Lecture.toJSON().toString());
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 4:
                    System.out.println("제거할 강의 id: ");
                    String temp_d_lectureId = new Scanner(System.in).nextLine();
                    ans = apiClient.httpDelete("lectures", "id=" + temp_d_lectureId);
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 5:
                    ans = apiClient.httpGet("students", "id=");
                    System.out.println("DB 처리결과: " + ans.toString(2));
                    break;

                case 6:
                    ans = apiClient.httpGet("lectures", "id=");
                    System.out.println("DB 처리결과: " + ans.toString(2));
                    break;

                case 7: // 학생 시간표 조회
                    System.out.println("학생 시간표:");
                    for (Lecture lectures : targetStudent.getTimetable().getLectures()) {
                        System.out.println(lectures.getName() + " - " + lectures.getDate() + ", " + lectures.getStartTime() + " ~ " + lectures.getEndTime());
                    }
                    break;
                case 8: // 학생 시간표 수정
                    // 예제: 시간표에 강의 추가
                    System.out.println("추가할 강의 ID를 입력하세요.");
                    String lectureIdToAdd = new Scanner(System.in).nextLine();
                    Lecture lectureToAdd = new Lecture(apiClient.httpGet("lectures", "id=" + lectureIdToAdd));
                    targetStudent.addToTimetable(lectureToAdd);
                    System.out.println("강의가 시간표에 추가되었습니다.");
                    break;

                default:
                    System.out.println("Usage: 0 ~ 6");
                    break;
            }

            if(exitFlag){
                System.out.println("종료");
                break;
            }
        }
    }
}
