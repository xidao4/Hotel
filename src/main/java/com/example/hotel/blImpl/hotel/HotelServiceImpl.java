package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.sql.Types.NULL;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CouponService couponService;


    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
//        User manager = accountService.getUserInfo(hotelVO.getManagerId());
//        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
//            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
//        }
        Hotel hotel = new Hotel();
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));

        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setRate(hotelVO.getRate());
        hotel.setDescription(hotelVO.getDescription());
        //hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels(Integer userid) {
        List<HotelVO> hotelVOS = hotelMapper.selectAllHotel();
        for (HotelVO hotelVO : hotelVOS) {
            boolean hasOrderedBefore = orderService.hasOrderedBefore(userid, hotelVO.getId());
            hotelVO.setHasOrderedBefore(hasOrderedBefore);
            List<String> couponNames = couponService.getHotelAllCouponName(hotelVO.getId());
            hotelVO.setCouponNames(couponNames);
        }
        return hotelVOS;
    }

    @Override
    public ResponseVO getManagerId(Integer hotelId) {
        System.out.println(hotelId);
        HotelVO hotelVO=hotelMapper.selectById(hotelId);
        System.out.println(hotelVO.getId());
//        try{
//            hotelVO.getManagerId();
//            return ResponseVO.buildSuccess(true);
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//            return ResponseVO.buildFailure("该酒店暂时没有工作人员账号");
//        }
        System.out.println(hotelVO.getName());
        System.out.println(hotelVO.getManagerId());
        if(hotelVO.getManagerId()==null){
            return ResponseVO.buildSuccess(false);
        }else{
            return ResponseVO.buildSuccess(true);
        }
    }

    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId, Integer userid) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);
        hotelVO.setHasOrderedBefore(orderService.hasOrderedBefore(userid, hotelId));
        hotelVO.setCouponNames(couponService.getHotelAllCouponName(hotelId));
        return hotelVO;
    }

    @Override
    public HotelVO getHotelInfo(Integer hotelId) {
        return hotelMapper.selectById(hotelId);
    }

    @Override
    public List<HotelVO> retrieveHotelByDate(Integer userid, String checkInDate, String checkOutDate) {
        List<HotelVO> hotelVOS = retrieveHotels(userid);
        List<HotelVO> hotelVOSByDate = new ArrayList<>();
        for(HotelVO hotelVO: hotelVOS){
            List<Order> orders = orderService.getHotelOrders(hotelVO.getId());
            int curRoomNum = getTotalRoomNum(hotelVO.getId());
            for(Order order: orders){
                if((checkInDate.compareTo(order.getCheckInDate())>=0 && checkInDate.compareTo(order.getCheckOutDate())<=0) || checkOutDate.compareTo(order.getCheckInDate())>=0 && checkOutDate.compareTo(order.getCheckOutDate())<=0){
                    curRoomNum -= order.getRoomNum();
                }
            }
            if(curRoomNum>0){
                hotelVOSByDate.add(hotelVO);
            }
        }
        return hotelVOSByDate;
    }

    @Override
    public int getTotalRoomNum(Integer hotelId){
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfo(hotelId);
        int num = 0;
        for(HotelRoom hotelRoom: hotelRooms){
            num += hotelRoom.getTotal();
        }
        return num;
    }

    /*@Override
    public int getRoomNumOfTypicalType(HotelVO hotelVO, String roomType){
        int num = 0;
        for(RoomVO roomVO: hotelVO.getRooms()){
            if(roomVO.getRoomType()==roomType){
                num += roomVO.getTotal();
            }
        }
        return num;
    }*/

    @Override
    public int getHotelId(Integer managerId){
        List<HotelVO> hotelVOS=hotelMapper.selectAllHotel();
        for(HotelVO hotelVO:hotelVOS){
            if(hotelVO.getManagerId() == managerId){
                return hotelVO.getId();
            }
        }
        return -1000;
    }

    @Override
    public ResponseVO updateHotelInfo(int id, String address, String phoneNum) {
        try {
            hotelMapper.update(id, address, phoneNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("更新酒店基本信息失败");
        }
        return ResponseVO.buildSuccess(true);
    }

}
