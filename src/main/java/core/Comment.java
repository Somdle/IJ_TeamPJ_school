package core;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
    private String id; // 댓글 식별자
    private String commentId; // 댓글 id
    private String content; // 내용
    private String author; // 작성자
    private String creationTime; // 작성 시간

    public Comment(String id, String commentId, String content, String author, String creationTime) {
        this.id = id;
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.creationTime = creationTime;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_id", this.id);
        json.put("commentId", this.commentId);
        json.put("content", this.content);
        json.put("author", this.author);
        json.put("creationTime", this.creationTime);
        return json;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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
}
