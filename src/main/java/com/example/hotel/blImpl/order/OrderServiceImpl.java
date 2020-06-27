package com.example.hotel.blImpl.order;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.blImpl.credit.DefaultDefaultCreditIncreImpl;
import com.example.hotel.data.curRoom.CurRoomMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.MemberMapper;
import com.example.hotel.po.CurRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.DefaultUpdateCreditVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ANNUL_ERROR = "撤销失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    CurRoomMapper curRoomMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    CreditService creditService;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        System.out.println("-------------------------------");
        int reserveRoomNum = orderVO.getRoomNum();
        System.out.println("+++++++++++++++++++++++++++++++++");
        int hotelid=orderVO.getHotelId();
        orderVO.setWhetherComment(0);
        System.out.println("时间");
        System.out.println(orderVO.getCheckInDate());
        System.out.println(orderVO.getCheckOutDate());
        //这一段是处理从具体的住房日期的，把日期都放在了days里
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(orderVO.getCheckInDate());
            Date end = dateFormat.parse(orderVO.getCheckOutDate());

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
        if(reserveRoomNum>roomMapper.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType())){
            return ResponseVO.buildSuccess(ROOMNUM_LACK);
        }
        //下面这个for循环用来看这段日期里是否有记录、如果有记录看每天房间数是否大于0；最终判断可用房间数够不够
        int signal=1;
        //signal默认为1，表示房间数是够的，当房间数小于等于0时置为0
        for(String day: days){
            System.out.println(day);
            System.out.println("assssssssssssss");
            System.out.println(curRoomMapper.isExist(orderVO.getHotelId(),orderVO.getRoomType(),day));
            if(curRoomMapper.isExist(orderVO.getHotelId(),orderVO.getRoomType(),day)!=null){
                int roomNum=curRoomMapper.selectCurRoomNum(orderVO.getHotelId(),orderVO.getRoomType(),day);
                if(roomNum<=0){
                    signal=0;
                }
            }
        }
        if(signal==0){
            return ResponseVO.buildSuccess(ROOMNUM_LACK);
        }
        else{
            for(String day:days){
                if(curRoomMapper.isExist(orderVO.getHotelId(),orderVO.getRoomType(),day)!=null){
                    curRoomMapper.updateCurRoomNum(orderVO.getHotelId(),orderVO.getRoomType(),day,orderVO.getRoomNum());
                }
                else{
                    CurRoom curRoom=new CurRoom();
                    curRoom.setHotelId(orderVO.getHotelId());
                    curRoom.setRoomType(orderVO.getRoomType());
                    curRoom.setCurRoomNum(roomMapper.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType()));
                    curRoom.setDayTime(day);
                    curRoomMapper.insertCurRoom(curRoom);
                    curRoomMapper.updateCurRoomNum(orderVO.getHotelId(),orderVO.getRoomType(),day,orderVO.getRoomNum());
                }
            }
        }


        String type=orderVO.getRoomType();
        String phoneNumber=orderVO.getPhoneNumber();
        System.out.println(phoneNumber);
        String residentName=orderVO.getResidentName();
        System.out.println(residentName);
//        int curNum = hotelService.getRoomCurNum(hotelid,type);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
//        if(reserveRoomNum>curNum){
//            return ResponseVO.buildFailure(ROOMNUM_LACK);
//        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            System.out.println("-------------------------------");
            orderVO.setOrderState("已预订");
            orderVO.setCancelReason("未撤销");
            orderVO.setWhetherComment(0);
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
//            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getOrderByHotelId(int hotelId){
        return orderMapper.getOrderByHotelId(hotelId);
    }
    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public boolean hasOrderedBefore(int userid, int hotelid) {
        List<Order> orders = getUserOrders(userid);
        boolean res = false;
        for (Order order : orders) {
            if (order.getHotelId() == hotelid) {
                res = true;
                break;
            }
        }
        return res;
    }

    @Override
    public Order getOrder(int orderid){
        return orderMapper.getOrderById(orderid);
    }

    @Transactional
    @Override
    public ResponseVO annulOrder(int orderid,String reason,int hotelId,String roomType,String startTime,String endTime,int reserveNum) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        try {
            Order order = orderMapper.getOrderById(orderid);
            if(order.getOrderState().equals("已执行")){
                return ResponseVO.buildFailure(ANNUL_ERROR);
            }
            /**
             * 因为Order表还未增加"最晚执行时间"属性，所以暂时无法实现信用值变更
             */
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//            long createTime = sf.parse(order.getCreateDate()).getTime();
//            long nowTime = new Date(System.currentTimeMillis()).getTime();
//
//            User user = accountService.getUserInfo(order.getUserId());
            System.out.println("OrderServiceImpl");
            System.out.println(orderid);
            System.out.println(reason);
            hotelService.updateRoomInfo(order.getHotelId(), order.getRoomType(), -order.getRoomNum());
            orderMapper.annulOrder(orderid,reason);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }

        //by ydl
        System.out.println("撤销");
            List<String> days = new ArrayList<String>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date start = dateFormat.parse(startTime);
                Date end = dateFormat.parse(endTime);

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
            for(String day:days){
                System.out.println(-reserveNum);
                curRoomMapper.updateCurRoomNum(hotelId,roomType,day,-reserveNum);
            }


        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public ResponseVO changeStatus(int orderid,String status,int hotelId,String roomType,String startTime,String endTime,int reserveNum) {
        orderMapper.changeStatus(orderid, status);
        System.out.println(reserveNum);
        //by ljy
        if(status.equals("已执行")){//如果改为“已执行”，
            int userId=orderMapper.getUserId(orderid);
            if(memberMapper.getInfo(userId)!=null){//,且客户是会员,
                Double amount=orderMapper.getPriceById(orderid);
                //double->int
                accountService.increaseMemberPoints(userId,(int)(amount*3));//则更新会员积分
            }
            DefaultUpdateCreditVO defaultUpdateCreditVO=new DefaultUpdateCreditVO();
            defaultUpdateCreditVO.setOrderId(orderid);
            defaultUpdateCreditVO.setDesc("正常执行订单增加信用");
            System.out.println("正常执行订单增加信用");
            creditService.defaultUpdateCredit(defaultUpdateCreditVO, new DefaultDefaultCreditIncreImpl());
        }

        //by ydl
        System.out.println("撤销");
        if(status.equals("酒店撤销") || status.equals("异常")){
            List<String> days = new ArrayList<String>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date start = dateFormat.parse(startTime);
                Date end = dateFormat.parse(endTime);

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
            for(String day:days){
                System.out.println(-reserveNum);
                curRoomMapper.updateCurRoomNum(hotelId,roomType,day,-reserveNum);
            }
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void changeCommentStatus(int orderId){
        orderMapper.changeCommentStatus(orderId);
    }
    @Override
    public Double getPrice(int orderId) {
        return orderMapper.getPriceById(orderId);
    }

    @Override
    public Integer getUserId(int orderId) {
        return orderMapper.getUserId(orderId);
    }
}
