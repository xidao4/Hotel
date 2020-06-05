package com.example.hotel.po;

/**
 * ydl
 */

public class Comment {
    private Integer id;
    private Integer userId;
    private Integer hotelId;
    private Integer commentValue;
    private String commentContent;
    private String avatar;
    private String userName;
    private String reply;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getCommentValue() {
        return commentValue;
    }

    public void setCommentValue(Integer commentValue) {
        this.commentValue = commentValue;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }
}
