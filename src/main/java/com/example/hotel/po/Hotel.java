package com.example.hotel.po;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;

public class Hotel {
    private Integer id;
    private String hotelName;
    private String address;
    /**
     * 商圈
     */
    private BizRegion bizRegion;
    private HotelStar hotelStar;
    private Double rate;
    private String description;
    private String phoneNum;
    private String pic_1;
    private String pic_2;
    private String pic_3;
    private String pic_4;
    private String pic_5;
    private String pic_6;
    private String pic_7;
    private String pic_8;
    /**
     * 酒店管理员id
     */
    private Integer managerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BizRegion getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(BizRegion bizRegion) {
        this.bizRegion = bizRegion;
    }

    public HotelStar getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(HotelStar hotelStar) {
        this.hotelStar = hotelStar;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
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

