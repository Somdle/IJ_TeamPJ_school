package core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    private List<Post> posts;

    public Board() {
        this.posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(String title, String content, String author) {
        String id = UUID.randomUUID().toString();
        String creationTime = String.valueOf(System.currentTimeMillis());
        Post newPost = new Post(id, title, content, author, creationTime, new ArrayList<>());
        this.posts.add(newPost);
    }

    public void deletePost(String postId) {
        this.posts.removeIf(post -> post.getId().equals(postId));
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(this.posts);
    }

    public void addCommentToPost(String postId, String content, String author) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                String commentId = UUID.randomUUID().toString();
                String creationTime = String.valueOf(System.currentTimeMillis());
                Comment newComment = new Comment(commentId, commentId, content, author, creationTime);
                post.getComments().add(newComment);
                break;
            }
        }
    }

    public void deleteCommentFromPost(String postId, String commentId) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                post.getComments().removeIf(comment -> comment.getId().equals(commentId));
                break;
            }
        }
    }

    public List<Comment> getAllComments(String postId) {
        for (Post post : posts) {
            if (post.getId().equals(postId)) {
                return new ArrayList<>(post.getComments());
            }
        }
        return new ArrayList<>();
    }
}
