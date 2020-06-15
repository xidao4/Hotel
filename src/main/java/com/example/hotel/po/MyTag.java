package com.example.hotel.po;

public class MyTag {
    public Integer getId() {
        return id;
    }

    public void setId(Integer tagId) {
        this.id = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    private Integer id;
    private String tagName;
    private Integer hotelId;

}
