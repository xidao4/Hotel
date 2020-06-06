package com.example.hotel.vo;

/**
 * @Author: syc
 */
public class HotelVIPSpecialCouponVO extends CouponVO {
    private Integer hotelId;
    private double discountSilver;
    private double discountGold;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getDiscountSilver() {
        return discountSilver;
    }

    public void setDiscountSilver(double discountSilver) {
        this.discountSilver = discountSilver;
    }

    public double getDiscountGold() {
        return discountGold;
    }

    public void setDiscountGold(double discountGold) {
        this.discountGold = discountGold;
    }
}
