package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.MemberMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private AccountService accountService;

    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        if(coupon.getCouponType()!=1) return false;
        String createDate = orderVO.getCreateDate().substring(5);
        User user = accountService.getUserInfo(orderVO.getUserId());
        String birthday = user.getBirthday();
        if(orderVO.getCreateDate().substring(5).equals(birthday.substring(5))){
            return true;
        }
        return false;
    }
}
