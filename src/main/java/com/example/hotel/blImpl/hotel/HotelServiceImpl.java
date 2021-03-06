package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.hotel.TagService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.curRoom.CurRoomMapper;
import com.example.hotel.data.hotel.CommentMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.*;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private CurRoomMapper curRoomMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RoomMapper roomMapper;


    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {

        Hotel hotel = new Hotel();
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));

        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setRate(hotelVO.getRate());
        hotel.setDescription(hotelVO.getDescription());

        String star=hotelVO.getHotelStar();
        System.out.println("酒店的星级"+star);
//        String val="";
//        if(star.equals("3")){
//            val="三星级";
//        }else if(star.equals("4")){
//            val="四星级";
//        }else if(star.equals("5")){
//            val="五星级";
//        }
//        System.out.println("enums"+HotelStar.valueOf(val));
//
//        hotel.setHotelStar(HotelStar.valueOf(val));

        HotelStar val;
        if(star.equals("3")){
            val=HotelStar.Three;
        }else if(star.equals("4")){
            val=HotelStar.Four;
        }else {
            val=HotelStar.Five;
        }
        hotel.setHotelStar(val);
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
    public List<HotelVO> getAllHotels() {
        return hotelMapper.selectAllHotel();
    }

    @Override
    public List<HotelCardVO> getHotelCardInfos(Integer userId) {
        List<HotelVO> hotelVOS = retrieveHotels(userId);
        ArrayList<HotelCardVO> hotelCardVOS = new ArrayList<>();
        for(HotelVO hotelVO: hotelVOS) {
            HotelCardVO hotelCardVO = new HotelCardVO();
            BeanUtils.copyProperties(hotelVO, hotelCardVO);
            hotelCardVO.setTags(tagService.getTagsByHotelId(hotelCardVO.getId()));
            hotelCardVO.setCommentNum(commentMapper.getCommentNumByHotelId(hotelCardVO.getId()));
            hotelCardVO.setMinPrice(roomMapper.getMinPriceByHotelId(hotelCardVO.getId()));
            hotelCardVOS.add(hotelCardVO);
        }
        return hotelCardVOS;
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
    public HotelVO retrieveHotelDetails(Integer hotelId, Integer userid) {
        System.out.println(hotelId);
        System.out.println(userid);
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
        HotelVO hotel= hotelMapper.selectById(hotelId);
//        HotelVO vo=new HotelVO();
//        vo.setBizRegion(hotel.getBizRegion().name());
//        vo.setAddress(hotel.getAddress());
//        vo.setDescription(hotel.getDescription());
//        vo.setHotelStar(hotel.getHotelStar().name());
//        vo.setRate(hotel.getRate());
//        vo.setName(hotel.getHotelName());
//        vo.setId(hotel.getId());
//        vo.setPhoneNum(hotel.getPhoneNum());
//        vo.setManagerId(hotel.getManagerId());
        return hotel;
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
    public List<HotelCardVO> retrieveHotelByDate(Integer userid, String checkInDate, String checkOutDate) {
        List<HotelCardVO> hotelVOS = getHotelCardInfos(userid);
        List<HotelCardVO> hotelVOSByDate = new ArrayList<>();
        //ydl
        //这一段是处理从入住到退房之间有哪些日期的，具体日期放在了days里
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(checkInDate);
            Date end = dateFormat.parse(checkOutDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(HotelCardVO hotelCardVO: hotelVOS){
//            List<Order> orders = orderService.getHotelOrders(hotelVO.getId());
//            int curRoomNum = getTotalRoomNum(hotelVO.getId());
//            for(Order order: orders){
//                if((checkInDate.compareTo(order.getCheckInDate())>=0 && checkInDate.compareTo(order.getCheckOutDate())<=0) || checkOutDate.compareTo(order.getCheckInDate())>=0 && checkOutDate.compareTo(order.getCheckOutDate())<=0){
//                    curRoomNum -= order.getRoomNum();
//                }
//            }
//
//            if(curRoomNum>0){
//                hotelVOSByDate.add(hotelVO);
//            }
            int bigSignal=1;
            for(String day:days){
                if(curRoomMapper.isExist(hotelCardVO.getId(),"BigBed",day)!=null){
                    int roomNum=curRoomMapper.selectCurRoomNum(hotelCardVO.getId(),"BigBed",day);
                    if(roomNum<=0){
                        bigSignal=0;
                    }
                }
            }
            int doubleSignal=1;
            for(String day:days){
                if(curRoomMapper.isExist(hotelCardVO.getId(),"DoubleBed",day)!=null){
                    int roomNum=curRoomMapper.selectCurRoomNum(hotelCardVO.getId(),"BigBed",day);
                    if(roomNum<=0){
                        doubleSignal=0;
                    }
                }
            }
            int familySignal=1;
            for(String day:days){
                if(curRoomMapper.isExist(hotelCardVO.getId(),"Family",day)!=null){
                    int roomNum=curRoomMapper.selectCurRoomNum(hotelCardVO.getId(),"BigBed",day);
                    if(roomNum<=0){
                        familySignal=0;
                    }
                }
            }
            if(bigSignal==0 && doubleSignal==0 && familySignal==0){
                System.out.println("这家酒店的三种房间在这段时间内都不够了");
            }
            else{
                hotelVOSByDate.add(hotelCardVO);
            }
        }
        System.out.println("size:");
        System.out.println(hotelVOSByDate.size());
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
    public ResponseVO updateHotelInfo(int id, String address, String phoneNum,String description) {
        try {
            hotelMapper.update(id, address, phoneNum,description);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("更新酒店基本信息失败");
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO changePic(List<HotelPicVO> hotelPicVOList, int id) {
        String[] pics = {"","","","","","","",""};
        int ptr = 0;
        try{
            for(HotelPicVO hotelPicVO: hotelPicVOList){
                //System.out.println(hotelPicVO.getUrl());
                //System.out.println(hotelPicVO.getUrl().substring(49));
                if(!hotelPicVO.getStatus().equals("removed")){
                    pics[ptr++] = hotelPicVO.getUrl().substring(49);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("更新酒店图片失败");
        }
        hotelMapper.updatePic(id,pics[0],pics[1],pics[2],pics[3],pics[4],pics[5],pics[6],pics[7]);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO getPics(int hotelId) {
        List<HotelPicVO> hotelPicVOS = new ArrayList<>();
        String status = "done";
        String prefix = "https://supernatural.oss-cn-beijing.aliyuncs.com/";
        String name;
        Integer uid = 0;
        HotelVO hotel = hotelMapper.selectById(hotelId);
        if(hotel.getPic_1().length()>0){
            name = hotel.getPic_1();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_2().length()>0){
            name = hotel.getPic_2();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_3().length()>0){
            name = hotel.getPic_3();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_4().length()>0){
            name = hotel.getPic_4();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_5().length()>0){
            name = hotel.getPic_5();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_6().length()>0){
            name = hotel.getPic_6();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_7().length()>0){
            name = hotel.getPic_7();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        if(hotel.getPic_8().length()>0){
            name = hotel.getPic_8();
            hotelPicVOS.add(new HotelPicVO(prefix+name,uid++,name,status));
        }
        return ResponseVO.buildSuccess(hotelPicVOS);
    }
}
