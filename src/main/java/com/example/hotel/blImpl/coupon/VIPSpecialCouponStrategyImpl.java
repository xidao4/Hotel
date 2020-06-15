package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VIPSpecialCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!=6) return false;
        int membership = accountService.getMemInfo(orderVO.getUserId()).getMembership();
        if(membership!=0)return true;
        return false;
    }
}
