package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class FestivalCouponStrategyImpl implements CouponMatchStrategy {
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!=5) return false;
        String createDate = orderVO.getCreateDate().substring(5);
        if(createDate.equals("01-01") || createDate.equals("02-14") || createDate.equals("12-25")){
            return true;
        }
        return false;
    }
}
