package com.example.hotel.vo;

public class HotelTargetRoomNumCouponVO extends CouponVO {
    private Integer hotelId;
    private Double discount;
    private Integer targetRoomNum;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getTargetRoomNum() {
        return targetRoomNum;
    }

    public void setTargetRoomNum(Integer targetRoomNum) {
        this.targetRoomNum = targetRoomNum;
    }
}
