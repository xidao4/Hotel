package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CouponServiceImpl implements CouponService {


    private final  TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;
    private final RoomNumCouponStrategyImpl roomNumCouponStrategy;
    private final  TimeCouponStrategyImpl timeCouponStrategy;
    private final BirthdayCouponStrategyImpl birthdayCouponStrategy;
    private final FestivalCouponStrategyImpl festivalCouponStrategy;
    private final CouponMapper couponMapper;

    private static List<CouponMatchStrategy> strategyList = new ArrayList<>();

    @Autowired
    public CouponServiceImpl(TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy,
                             TimeCouponStrategyImpl timeCouponStrategy,
                             RoomNumCouponStrategyImpl roomNumCouponStrategy,
                             BirthdayCouponStrategyImpl birthdayCouponStrategy,
                             FestivalCouponStrategyImpl festivalCouponStrategy,
                             CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
        this.targetMoneyCouponStrategy = targetMoneyCouponStrategy;
        this.timeCouponStrategy = timeCouponStrategy;
        this.roomNumCouponStrategy=roomNumCouponStrategy;
        this.birthdayCouponStrategy=birthdayCouponStrategy;
        this.festivalCouponStrategy=festivalCouponStrategy;
        //strategyList.add(roomNumCouponStrategy);
        strategyList.add(targetMoneyCouponStrategy);
        //strategyList.add(timeCouponStrategy);
    }



    @Override
    public List<Coupon> getMatchOrderCoupon(OrderVO orderVO) {

        List<Coupon> hotelCoupons = getHotelAllCoupon(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        return availAbleCoupons;
    }

    @Override
    public List<Coupon> getHotelAllCoupon(Integer hotelId) {
        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(hotelId);
        return hotelCoupons;
    }

    @Override
    public List<String> getHotelAllCouponName(Integer hotelId) {
        List<Coupon> hotelCoupons = getHotelAllCoupon(hotelId);
        List<String> couponNames = new ArrayList<>();
        for(Coupon coupon: hotelCoupons){
            if(coupon.getStatus()==1){
                couponNames.add(coupon.getCouponName());
            }
        }
        return couponNames;
    }

    @Override
    public CouponVO addHotelTargetMoneyCoupon(HotelTargetMoneyCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetMoney(couponVO.getTargetMoney());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountMoney(couponVO.getDiscountMoney());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelTimeCoupon(TimeCouponVO timeCouponVO){
        Coupon coupon = new Coupon();
        coupon.setCouponName(timeCouponVO.getName());
        coupon.setDescription(timeCouponVO.getDescription());
        coupon.setCouponType(timeCouponVO.getType());
        coupon.setDiscount(timeCouponVO.getDiscount());
        coupon.setStartTime(timeCouponVO.getStartTime());
        coupon.setEndTime(timeCouponVO.getEndTime());
        coupon.setHotelId(timeCouponVO.getHotelId());
        coupon.setStatus(1);
        int result=couponMapper.insertCoupon(coupon);
        timeCouponVO.setId(result);
        return timeCouponVO;
    }

    @Override
    public CouponVO addHotelTargetRoomNumCoupon(HotelTargetRoomNumCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setTargetRoomNum(couponVO.getTargetRoomNum());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelBirthdayCoupon(HotelBirthdayCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelFestivalCoupon(HotelFestivalCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscount(couponVO.getDiscount());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }

    @Override
    public CouponVO addHotelVIPSpecialCoupon(HotelVIPSpecialCouponVO couponVO) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponVO.getName());
        coupon.setDescription(couponVO.getDescription());
        coupon.setCouponType(couponVO.getType());
        coupon.setHotelId(couponVO.getHotelId());
        coupon.setDiscountSilver(couponVO.getDiscountSilver());
        coupon.setDiscountGold(couponVO.getDiscountGold());
        coupon.setStatus(1);
        int result = couponMapper.insertCoupon(coupon);
        couponVO.setId(result);
        return couponVO;
    }
}
