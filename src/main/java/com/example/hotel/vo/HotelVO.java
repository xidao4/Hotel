package com.example.hotel.vo;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.User;

import java.util.List;

public class HotelVO {
    private Integer id;
    private String name;
    private String address;
    private String bizRegion;
    private String hotelStar;
    private Double rate;
    private String description;
    private String phoneNum;
    private Integer managerId;
    private List<RoomVO> rooms;
    private boolean hasOrderedBefore;
    private List<String> couponNames;
    private String pic_1;
    private String pic_2;
    private String pic_3;
    private String pic_4;
    private String pic_5;
    private String pic_6;
    private String pic_7;
    private String pic_8;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<RoomVO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomVO> rooms) {
        this.rooms = rooms;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public boolean isHasOrderedBefore() {
        return hasOrderedBefore;
    }

    public void setHasOrderedBefore(boolean hasOrderedBefore) {
        this.hasOrderedBefore = hasOrderedBefore;
    }

    public List<String> getCouponNames() {
        return couponNames;
    }

    public void setCouponNames(List<String> couponNames) {
        this.couponNames = couponNames;
    }

    public String getPic_1() {
        return pic_1;
    }

    public void setPic_1(String pic_1) {
        this.pic_1 = pic_1;
    }

    public String getPic_2() {
        return pic_2;
    }

    public void setPic_2(String pic_2) {
        this.pic_2 = pic_2;
    }

    public String getPic_3() {
        return pic_3;
    }

    public void setPic_3(String pic_3) {
        this.pic_3 = pic_3;
    }

    public String getPic_4() {
        return pic_4;
    }

    public void setPic_4(String pic_4) {
        this.pic_4 = pic_4;
    }

    public String getPic_5() {
        return pic_5;
    }

    public void setPic_5(String pic_5) {
        this.pic_5 = pic_5;
    }

    public String getPic_6() {
        return pic_6;
    }

    public void setPic_6(String pic_6) {
        this.pic_6 = pic_6;
    }

    public String getPic_7() {
        return pic_7;
    }

    public void setPic_7(String pic_7) {
        this.pic_7 = pic_7;
    }

    public String getPic_8() {
        return pic_8;
    }

    public void setPic_8(String pic_8) {
        this.pic_8 = pic_8;
    }
}
