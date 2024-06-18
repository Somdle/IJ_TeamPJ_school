package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.List;

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

        // 추가한 코드(다솔)
        System.out.println("9. 게시물 추가");
        System.out.println("10. 게시물 수정");
        System.out.println("11. 게시물 제거");
        System.out.println("12. 댓글 추가");
        System.out.println("13. 댓글 수정");
        System.out.println("14. 댓글 제거");

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
        Post post;
        Comment comment;

        while(true){
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

                    //다솔
//                case 9: // 게시물 추가
//                    scanner = new Scanner(System.in);
//                    System.out.print("제목을 입력하세요: ");
//                    String title = scanner.nextLine();
//                    System.out.print("내용을 입력하세요: ");
//                    String content = scanner.nextLine();
//                    System.out.print("작성자를 입력하세요: ");
//                    String author = scanner.nextLine();
//
//                    // 새로운 게시물 생성
//                    Post newPost = new Post(null, title, content, author, null, new ArrayList<>());
//
//                    // 서버에 추가 요청
//                    JSONObject postResponse = apiClient.httpPost("posts", "", newPost.toJSON().toString());
//                    System.out.println("DB 처리결과: " + postResponse);
//                    break;
//                case 10: // 게시물 수정
//                    System.out.print("수정할 게시물 ID를 입력하세요: ");
//                    String postIdToUpdate = new Scanner(System.in).nextLine();
//
//                    // 기존 게시물 가져오기
//                    JSONObject existingPostJson = apiClient.httpGet("posts", "id=" + postIdToUpdate);
//                    Post existingPost = new Post(existingPostJson.optString("_id"),
//                            existingPostJson.optString("title"),
//                            existingPostJson.optString("content"),
//                            existingPostJson.optString("author"),
//                            existingPostJson.optString("creationTime"),
//                            new ArrayList<>());
//
//                    // 새로운 내용 입력받기
//                    System.out.print("새로운 제목을 입력하세요: ");
//                    existingPost.setTitle(new Scanner(System.in).nextLine());
//                    System.out.print("새로운 내용을 입력하세요: ");
//                    existingPost.setContent(new Scanner(System.in).nextLine());
//
//                    // 서버에 수정 요청
//                    JSONObject updateResponse = apiClient.httpPut("posts", "id=" + postIdToUpdate, existingPost.toJSON().toString());
//                    System.out.println("DB 처리결과: " + updateResponse);
//                    break;
//                case 11: // 게시물 제거
//                    System.out.print("삭제할 게시물 ID를 입력하세요: ");
//                    String postIdToDelete = new Scanner(System.in).nextLine();
//
//                    // 서버에 삭제 요청
//                    JSONObject deleteResponse = apiClient.httpDelete("posts", "id=" + postIdToDelete);
//                    System.out.println("DB 처리결과: " + deleteResponse);
//                    break;
//                case 12: // 댓글 추가
//                    System.out.println("게시물 ID를 입력하세요:");
//                    String postIdToAddComment = new Scanner(System.in).nextLine();
//                    System.out.println("댓글 내용을 입력하세요:");
//                    String commentContentToAdd = new Scanner(System.in).nextLine();
//
//                    // 댓글 객체 생성
//                    Comment newComment = new Comment(null, null, commentContentToAdd, "작성자", null);
//
//                    // 서버에 업로드
//                    JSONObject addCommentResponse = apiClient.httpPost("posts/comments", "postId=" + postIdToAddComment, newComment.toJSON().toString());
//                    System.out.println("댓글 추가 결과: " + addCommentResponse);
//                    break;
//                case 13: // 댓글 수정
//                    System.out.print("댓글을 수정할 게시물 ID를 입력하세요: ");
//                    String postIdToModifyComment = new Scanner(System.in).nextLine();
//
//                    // 기존 게시물 가져오기
//                    JSONObject postJsonToModifyComment = apiClient.httpGet("posts", "id=" + postIdToModifyComment);
//                    Post postToModifyComment = new Post(postJsonToModifyComment.optString("_id"),
//                            postJsonToModifyComment.optString("title"),
//                            postJsonToModifyComment.optString("content"),
//                            postJsonToModifyComment.optString("author"),
//                            postJsonToModifyComment.optString("creationTime"),
//                            new ArrayList<>());
//
//                    // 댓글 목록 출력
//                    List<Comment> comments = new ArrayList<>();
//                    JSONArray commentsJsonArray = postJsonToModifyComment.getJSONArray("comments");
//                    for (int i = 0; i < commentsJsonArray.length(); i++) {
//                        JSONObject commentJson = commentsJsonArray.getJSONObject(i);
//                        comment = new Comment(commentJson.optString("_id"),
//                                commentJson.optString("commentId"),
//                                commentJson.optString("content"),
//                                commentJson.optString("author"),
//                                commentJson.optString("creationTime"));
//                        comments.add(comment);
//                        System.out.println((i + 1) + ". " + commentJson.optString("content"));
//                    }
//
//                    // 수정할 댓글 선택
//                    System.out.print("수정할 댓글 번호를 선택하세요: ");
//                    int commentIndexToModify = Integer.parseInt(new Scanner(System.in).nextLine()) - 1;
//                    Comment commentToModify = comments.get(commentIndexToModify);
//
//                    // 새로운 내용 입력받기
//                    System.out.print("새로운 댓글 내용을 입력하세요: ");
//                    String newCommentContent = new Scanner(System.in).nextLine();
//                    commentToModify.setContent(newCommentContent);
//
//                    // 댓글 업데이트
//                    postToModifyComment.getComments().set(commentIndexToModify, commentToModify);
//
//                    // 서버에 업데이트 요청
//                    JSONObject modifyCommentResponse = apiClient.httpPut("posts", "id=" + postIdToModifyComment, postToModifyComment.toJSON().toString());
//                    System.out.println("댓글 수정 결과: " + modifyCommentResponse);
//                    break;
//                case 14: // 댓글 제거
//                    System.out.print("댓글을 삭제할 게시물 ID를 입력하세요: ");
//                    String postIdToDeleteComment = new Scanner(System.in).nextLine();
//
//                    // 기존 게시물 가져오기
//                    JSONObject postJsonToDeleteComment = apiClient.httpGet("posts", "id=" + postIdToDeleteComment);
//                    Post postToDeleteComment = new Post(postJsonToDeleteComment.optString("_id"),
//                            postJsonToDeleteComment.optString("title"),
//                            postJsonToDeleteComment.optString("content"),
//                            postJsonToDeleteComment.optString("author"),
//                            postJsonToDeleteComment.optString("creationTime"),
//                            new ArrayList<>());
//
//                    // 댓글 목록 출력
//                    List<Comment> commentsToDelete = new ArrayList<>();
//                    JSONArray commentsToDeleteJsonArray = postJsonToDeleteComment.getJSONArray("comments");
//                    for (int i = 0; i < commentsToDeleteJsonArray.length(); i++) {
//                        JSONObject commentJson = commentsToDeleteJsonArray.getJSONObject(i);
//                        comment = new Comment(commentJson.optString("_id"),
//                                commentJson.optString("commentId"),
//                                commentJson.optString("content"),
//                                commentJson.optString("author"),
//                                commentJson.optString("creationTime"));
//                        commentsToDelete.add(comment);
//                        System.out.println((i + 1) + ". " + commentJson.optString("content"));
//                    }
//
//                    // 삭제할 댓글 선택할 댓글 번호를 입력하세요: ");
//                    int commentIndexToDelete = Integer.parseInt(new Scanner(System.in).nextLine()) - 1;
//                    Comment commentToDelete = commentsToDelete.get(commentIndexToDelete);
//
//                    // 댓글 삭제
//                    postToDeleteComment.getComments().remove(commentIndexToDelete);
//
//                    // 서버에 업데이트 요청
//                    JSONObject deleteCommentResponse = apiClient.httpPut("posts", "id=" + postIdToDeleteComment, postToDeleteComment.toJSON().toString());
//                    System.out.println("댓글 삭제 결과: " + deleteCommentResponse);
//                    break;
                case 9: // 게시물 추가
                    Scanner postScanner = new Scanner(System.in);
                    System.out.print("게시물 ID: ");
                    postId = postScanner.nextLine();
                    System.out.print("게시물 제목: ");
                    String title = postScanner.nextLine();
                    System.out.print("게시물 내용: ");
                    String content = postScanner.nextLine();

                    // 작성자는 임시로 설정
                    String author = "user123";
                    // 현재 시간을 생성 시간으로 설정
                    String creationTime = String.valueOf(System.currentTimeMillis());

                    // 댓글 목록 초기화
                    ArrayList<Comment> comments = new ArrayList<>();

                    post = new Post(postId, postId, title, content, author, creationTime, comments);

                    ans = apiClient.httpPost("posts", "", post.toJSON().toString());
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 10: // 게시물 제거
                    System.out.println("제거할 게시물 ID를 입력하세요.");
                    String removePostId = new Scanner(System.in).nextLine();
                    ans = apiClient.httpDelete("posts", "postId=" + removePostId);
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 11: // 게시물 수정
                    System.out.println("수정할 게시물 ID를 입력하세요.");
                    String updatePostId = new Scanner(System.in).nextLine();
                    System.out.println("수정할 게시물 ID를 입력하세요.");
                    String updatedTitle = new Scanner(System.in).nextLine();
                    System.out.print("수정할 제목: ");
                    updatedTitle = new Scanner(System.in).nextLine();
                    System.out.print("수정할 내용: ");
                    String updatedContent = new Scanner(System.in).nextLine();

                    // 현재 시간을 수정 시간으로 설정
                    String updatedCreationTime = String.valueOf(System.currentTimeMillis());

                    // 기존의 게시물을 가져와 수정된 내용으로 업데이트
                    Post updatedPost = new Post(updatePostId, updatePostId, updatedTitle, updatedContent, "user123", updatedCreationTime, new ArrayList<>());

                    ans = apiClient.httpPut("posts", "postId=" + updatePostId, updatedPost.toJSON().toString());
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 12: // 댓글 추가
                    System.out.println("게시물 ID를 입력하세요.");
                    String targetPostId = new Scanner(System.in).nextLine();
                    System.out.println("추가할 댓글 내용을 입력하세요.");
                    String commentContent = new Scanner(System.in).nextLine();

                    // 작성자는 임시로 설정
                    String commentAuthor = "user123";
                    // 현재 시간을 생성 시간으로 설정
                    String commentCreationTime = String.valueOf(System.currentTimeMillis());

                    Comment newComment = new Comment("", "", commentContent, commentAuthor, commentCreationTime);

                    ans = apiClient.httpPost("comments", "postId=" + targetPostId, newComment.toJSON().toString());
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 13: // 댓글 제거
                    System.out.println("게시물 ID를 입력하세요.");
                    String postIdToRemoveComment = new Scanner(System.in).nextLine();
                    System.out.println("제거할 댓글 ID를 입력하세요.");
                    String commentIdToRemove = new Scanner(System.in).nextLine();

                    ans = apiClient.httpDelete("comments", "postId=" + postIdToRemoveComment + "&commentId=" + commentIdToRemove);
                    System.out.println("DB 처리결과: " + ans);
                    break;

                case 14: // 댓글 수정
                    System.out.println("게시물 ID를 입력하세요.");
                    String postIdToUpdateComment = new Scanner(System.in).nextLine();
                    System.out.println("수정할 댓글 ID를 입력하세요.");
                    String commentIdToUpdate = new Scanner(System.in).nextLine();
                    System.out.println("수정할 댓글 내용을 입력하세요.");
                    String updatedCommentContent = new Scanner(System.in).nextLine();

                    // 현재 시간을 수정 시간으로 설정
                    String updatedCommentCreationTime = String.valueOf(System.currentTimeMillis());

                    Comment updatedComment = new Comment("", commentIdToUpdate, updatedCommentContent, "user123", updatedCommentCreationTime);

                    ans = apiClient.httpPut("comments", "postId=" + postIdToUpdateComment + "&commentId=" + commentIdToUpdate, updatedComment.toJSON().toString());
                    System.out.println("DB 처리결과: " + ans);
                    break;

                default:
                    System.out.println("Usage: 0 ~ 14");
                    break;
            }

            if(exitFlag){
                System.out.println("종료");
                break;
            }
        }
    }
}
