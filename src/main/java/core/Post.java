package core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Post {
    private String id; // 게시물 식별자
    private String title; // 제목
    private String content; // 내용
    private String author; // 작성자
    private String creationTime; // 작성 시간
    private List<Comment> comments; // 댓글 목록

    public Post(String postId, String id, String title, String content, String author, String creationTime, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationTime = creationTime;
        this.comments = comments;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_id", this.id);
        json.put("title", this.title);
        json.put("content", this.content);
        json.put("author", this.author);
        json.put("creationTime", this.creationTime);

        // 댓글 목록을 JSON 배열로 변환하여 추가
        JSONArray commentsArray = new JSONArray();
        for (Comment comment : this.comments) {
            commentsArray.put(comment.toJSON());
        }
        json.put("comments", commentsArray);

        return json;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getPostId() {
//        return postId;
//    }
//
//    public void setPostId(String postId) {
//        this.postId = postId;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
