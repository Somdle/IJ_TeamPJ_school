package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static int printMenu() {
        System.out.println("메뉴");
        System.out.println("0. 종료");
        System.out.println("1. 강의 추가");
        System.out.println("2. 강의 제거");
        System.out.println("3. 강의 내용 추가");
        System.out.println("4. 강의 내용 제거");
        System.out.println("5. 전체 학생 조회");
        System.out.println("6. 전체 강의 조회");
        System.out.println("7. 학생 시간표 조회");
        System.out.println("8. 학생 시간표 수정");
        System.out.println("9. 게시물 추가");
        System.out.println("10. 게시물 수정");
        System.out.println("11. 게시물 제거");
        System.out.println("12. 댓글 추가");
        System.out.println("13. 댓글 수정");
        System.out.println("14. 댓글 제거");
        System.out.println("15. 게시물 목록 조회");
        System.out.println("16. 댓글 목록 조회");

        System.out.println("메뉴를 선택하세요: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    public static void main(String[] args) throws JSONException {
        ApiClient apiClient = new ApiClient("http://somdlesupport.iptime.org:3003/api", "pcu_project");
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);

        // 타겟 학생
        Student targetStudent = new Student(apiClient.httpGet("students", "id=학생 식별자"));

        // 시간표 사용 예시
        targetStudent.addToTimetable(new Lecture("1", "lecture1", "웹프로그래밍", "임선영 교수", "A 강의실", "2024-06-20", "9", "11", new ArrayList<>()));

        while (true) {
            // 중간에 나가는 옵션
            boolean exitFlag = false;

            // 강의 선택 자료
            JSONObject ans;
            String lectureId;
            String postId;
            String commentId;

            switch (printMenu()) {
                case 0:
                    System.exit(0);
                    exitFlag = true;
                    break;
                case 1: // 추가
                    System.out.println("추가할 강의 ID를 입력하세요.");
                    lectureId = scanner.nextLine();
                    ans = apiClient.httpPost("studentsLectures", "studentId=" + targetStudent.getId() + "&lectureId=" + lectureId, "");
                    System.out.println("DB 처리결과: " + ans);

                    Student student = new Student(apiClient.httpGet("students", "id=" + targetStudent.getId()));
                    Lecture lecture = new Lecture(apiClient.httpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                case 2: // 제거
                    System.out.println("제거할 강의 ID를 입력하세요.");
                    lectureId = scanner.nextLine();
                    ans = apiClient.httpDelete("studentsLectures", "studentId=" + targetStudent.getId() + "&lectureId=" + lectureId);
                    System.out.println("DB 처리결과: " + ans);

                    student = new Student(apiClient.httpGet("students", "id=" + targetStudent.getId()));
                    lecture = new Lecture(apiClient.httpGet("lectures", "id=" + lectureId));
                    System.out.println("학생 처리결과: " + student.getLecturesIds());
                    System.out.println("강의 처리결과: " + lecture.getStudents());
                    break;
                case 3: // 강의 추가
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
                    String temp_d_lectureId = scanner.nextLine();
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
                    String lectureIdToAdd = scanner.nextLine();
                    Lecture lectureToAdd = new Lecture(apiClient.httpGet("lectures", "id=" + lectureIdToAdd));
                    targetStudent.addToTimetable(lectureToAdd);
                    System.out.println("강의가 시간표에 추가되었습니다.");
                    break;
                case 9: // 게시물 추가
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("내용: ");
                    String content = scanner.nextLine();
                    System.out.print("작성자: ");
                    String author = scanner.nextLine();
                    board.addPost(title, content, author);
                    System.out.println("게시물이 추가되었습니다.");
                    break;
                case 10: // 게시물 수정
                    // 게시물 수정을 위해서는 postId, 새로운 제목, 새로운 내용을 받아와야 합니다.
                    // 이 기능은 구현되지 않았으므로, 게시물 제거 후 새로 추가하는 방식으로 대체할 수 있습니다.
                    break;
                case 11: // 게시물 제거
                    System.out.print("제거할 게시물 ID: ");
                    String postIdToRemove = scanner.nextLine();
                    board.deletePost(postIdToRemove);
                    System.out.println("게시물이 제거되었습니다.");
                    break;
                case 12: // 댓글 추가
                    System.out.print("게시물 ID: ");
                    String postIdToAddComment = scanner.nextLine();
                    System.out.print("댓글 내용: ");
                    String commentContent = scanner.nextLine();
                    System.out.print("작성자: ");
                    String commentAuthor = scanner.nextLine();
                    board.addCommentToPost(postIdToAddComment, commentContent, commentAuthor);
                    System.out.println("댓글이 추가되었습니다.");
                    break;
                case 13: // 댓글 수정
                    // 댓글 수정을 위해서는 commentId, 새로운 내용을 받아와야 합니다.
                    // 이 기능은 구현되지 않았으므로, 댓글 제거 후 새로 추가하는 방식으로 대체할 수 있습니다.
                    break;
                case 14: // 댓글 제거
                    System.out.print("게시물 ID: ");
                    String postIdToDeleteComment = scanner.nextLine();
                    System.out.print("제거할 댓글 ID: ");
                    String commentIdToDelete = scanner.nextLine();
                    board.deleteCommentFromPost(postIdToDeleteComment, commentIdToDelete);
                    System.out.println("댓글이 제거되었습니다.");
                    break;
                case 15: // 게시물 목록 조회
                    List<Post> posts = board.getAllPosts();
                    for (Post post : posts) {
                        System.out.println("ID: " + post.getId() + ", 제목: " + post.getTitle() + ", 내용: " + post.getContent() + ", 작성자: " + post.getAuthor());
                    }
                    break;
                case 16: // 댓글 목록 조회
                    System.out.print("게시물 ID: ");
                    String postIdToViewComments = scanner.nextLine();
                    List<Comment> comments = board.getAllComments(postIdToViewComments);
                    for (Comment comment : comments) {
                        System.out.println("ID: " + comment.getId() + ", 내용: " + comment.getContent() + ", 작성자: " + comment.getAuthor());
                    }
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }

            if (exitFlag) {
                System.out.println("종료");
                break;
            }
        }
    }
}
