package com.example.hotel.vo;

import com.example.hotel.enums.MessageType;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-05
 */
public class SendMessageVO {

    private String title;
    private String content;
    private Integer from;
    private List<Integer> tos;
    private MessageType type;
    private Integer retMsgId;

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

    public List<Integer> getTos() {
        return tos;
    }

    public void setTos(List<Integer> tos) {
        this.tos = tos;
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
