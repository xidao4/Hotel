package com.example.hotel.blImpl.hotel;

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
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
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

        return hotelVO;
    }

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
