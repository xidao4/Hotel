package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/hotelTime")
    public ResponseVO addHotelTimeCoupon(@RequestBody TimeCouponVO timeCouponVO){
        CouponVO couponVO = couponService.addHotelTimeCoupon(timeCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/hotelTargetRoomNum")
    public ResponseVO addHotelTargetRoomNumCoupon(@RequestBody HotelTargetRoomNumCouponVO hotelTargetRoomNumCouponVO){
        CouponVO couponVO = couponService.addHotelTargetRoomNumCoupon(hotelTargetRoomNumCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/hotelBirthday")
    public ResponseVO addHotelBirthdayCoupon(@RequestBody HotelBirthdayCouponVO hotelBirthdayCouponVO){
        CouponVO couponVO = couponService.addHotelBirthdayCoupon(hotelBirthdayCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }
    @PostMapping("/hotelFestival")
    public ResponseVO addHotelFestivalCoupon(@RequestBody HotelFestivalCouponVO hotelFestivalCouponVO){
        CouponVO couponVO = couponService.addHotelFestivalCoupon(hotelFestivalCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }
    /*@PostMapping("/hotelVIPSpecial")
    public ResponseVO addHotelVIPSpecialCoupon(@RequestBody HotelVIPSpecialCouponVO hotelVIPSpecialCouponVO){
        CouponVO couponVO = couponService.addHotelVIPSpecialCoupon(hotelVIPSpecialCouponVO);
        return ResponseVO.buildSuccess(couponVO);
    }*/

    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut,
                                           @RequestParam String createDate) {
        OrderVO requestOrderVO = new OrderVO();
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        requestOrderVO.setCheckInDate(checkIn);
        requestOrderVO.setCheckOutDate(checkOut);
        requestOrderVO.setCreateDate(createDate);
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


}
