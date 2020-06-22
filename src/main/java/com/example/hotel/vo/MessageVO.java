package com.example.hotel.vo;

import com.example.hotel.enums.MessageType;

import java.sql.Timestamp;

/**
 * @Author: wyc
 * @Date: 2020-06-05
 */
public class MessageVO {

    private Integer id;
    private String sendTime;
    private String status;
    private String title;
    private String content;
    private Integer from;
    private Integer to;
    private String fromName;
    private String toName;
    private MessageType type;
    private Integer retMsgId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Integer getRetMsgId() {
        return retMsgId;
    }

    public void setRetMsgId(Integer retMsgId) {
        this.retMsgId = retMsgId;
    }
}
