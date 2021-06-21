package com.example.tinternshipbackend.responses.like;

public class LikeHttp {
    String id;
    String fromUserId;
    String toUserId;
    boolean hasLiked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public boolean getHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

}
