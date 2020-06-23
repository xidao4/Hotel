package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.MemberMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {
    @Autowired
    private AccountService accountService;


    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //is not VIP
        if(accountService.getMemInfo(orderVO.getUserId())==null) return false;
        //is VIP
        if(coupon.getCouponType()!=1) return false;

        LocalDate checkIn= LocalDate.parse(orderVO.getCheckInDate());
        MonthDay in=MonthDay.of(checkIn.getMonth(),checkIn.getDayOfMonth());
        System.out.println("checkIn "+in);
        LocalDate checkOut=LocalDate.parse(orderVO.getCheckOutDate());
        MonthDay out=MonthDay.of(checkOut.getMonth(),checkOut.getDayOfMonth());
        System.out.println("checkOut "+out);
        LocalDate birthday=accountService.getMemInfo(orderVO.getUserId()).getBirthday();
        MonthDay birth=MonthDay.of(birthday.getMonth(),birthday.getDayOfMonth());
        System.out.println("birthday "+birth);

        //String createDate = orderVO.getCreateDate().substring(5);
        //User user = accountService.getUserInfo(orderVO.getUserId());
        //String birthday = user.getBirthday();

//        if((orderVO.getCreateDate()!="") && (orderVO.getCreateDate().substring(5).equals(birthday.substring(5)))){
//            return true;
//        }
        if(in.isAfter(birth)) return false;
        if(birth.isAfter(out)) return false;
        return true;
    }
}
