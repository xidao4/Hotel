package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.HotelTargetMoneyCouponVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author syc
 * @date 2020/06/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class couponTest {

    @Autowired
    CouponService couponService;

    @Test
    public void deleteCouponTest() {
        System.out.println(couponService.deleteCoupon(2));
    }

    @Test
    public void addHotelTargetMoneyCouponTest() {
        HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO = new HotelTargetMoneyCouponVO();
        hotelTargetMoneyCouponVO.setHotelId(1);
        hotelTargetMoneyCouponVO.setDiscountMoney(20);
        hotelTargetMoneyCouponVO.setTargetMoney(200);
        hotelTargetMoneyCouponVO.setName("满200减20");
        hotelTargetMoneyCouponVO.setDescription("lalallalalal");
        hotelTargetMoneyCouponVO.setType(3);
        hotelTargetMoneyCouponVO.setStatus(1);
        System.out.println(couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO));
    }
}
