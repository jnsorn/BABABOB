package kr.ac.hansung.bababob;
import java.util.ArrayList;


public class Like {
    ArrayList<String> likePostList;
    private String userId;
    private String postId;

    public Like() {
        likePostList = new ArrayList<String>();
    }

    public ArrayList<String> getLikePostList() {
        return likePostList;
    }

    public void setLikePostList(ArrayList<String> likePostList) {
        this.likePostList = likePostList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
