package com.example.hotel.vo;

/**
 * @Author: syc
 */
public class HotelVIPSpecialCouponVO extends CouponVO {
    private Integer hotelId;
    private Integer discountMoney;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }
}
