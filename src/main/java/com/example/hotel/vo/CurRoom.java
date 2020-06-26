package com.example.hotel.vo;

public class CurRoom {
    private Integer id;
    private Integer hotelId;
    private String roomType;
    private String daytime;
    private Integer curRoomNum;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCurRoomNum() {
        return curRoomNum;
    }

    public void setCurRoomNum(Integer curRoomNum) {
        this.curRoomNum = curRoomNum;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDayTime() {
        return daytime;
    }

    public void setDayTime(String time) {
        this.daytime = time;
    }
}
