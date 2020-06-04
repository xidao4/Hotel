package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        System.out.println("-------------------------------");
        int reserveRoomNum = orderVO.getRoomNum();
        System.out.println("+++++++++++++++++++++++++++++++++");
        int hotelid=orderVO.getHotelId();

        String type=orderVO.getRoomType();

        int curNum = hotelService.getRoomCurNum(hotelid,type);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            System.out.println("-------------------------------");
            orderVO.setOrderState("已预订");
            orderVO.setCancelReason("未撤销");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
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
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public Order getOrder(int orderid){
        return orderMapper.getOrderById(orderid);
    }

    @Transactional
    @Override
    public ResponseVO annulOrder(int orderid,String reason) {
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
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    @Override
    public ResponseVO changeStatus(int orderid, String status) {
        orderMapper.changeStatus(orderid, status);
        return ResponseVO.buildSuccess(true);
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
