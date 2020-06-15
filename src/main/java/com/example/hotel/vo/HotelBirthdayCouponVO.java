package com.example.hotel.vo;

/**
 * @Author: syc
 */
public class HotelBirthdayCouponVO extends CouponVO {

    private Integer hotelId;
    private double discount;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
