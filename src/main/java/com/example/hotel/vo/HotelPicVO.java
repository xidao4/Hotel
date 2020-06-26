package com.example.hotel.vo;

public class HotelPicVO {
    private String url;
    private Integer uid;
    private String name;
    private String status;

    public HotelPicVO(String url, Integer uid, String name, String status) {
        this.url = url;
        this.uid = uid;
        this.name = name;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
