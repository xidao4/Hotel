package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 返回指定用户是否预订过指定酒店
     * @param userid
     * @param hotelid
     * @return
     */
    boolean hasOrderedBefore(int userid, int hotelid);

    /**
     * 根据orderid获取对应的order记录
     * @param orderid
     * @return
     */
    Order getOrder(int orderid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid,String reason,int hotelId,String roomType,String startTime,String endTime,int reserveNum);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    /**
     * 酒店管理员更改订单状态
     * @param orderid
     * @param status
     * @return
     */
    ResponseVO changeStatus(int orderid,String status,int hotelId,String roomType,String startTime,String endTime,int reserveNum);

    /**
     * 改变order是否被评价的状态
     * @param orderId
     */
    void changeCommentStatus(int orderId);

    /**
     * 获得订单金额
     * @param orderId
     * @return
     */
    Double getPrice(int orderId);

    /**
     * 获得订单客户id
     * @param orderId
     * @return
     */
    Integer getUserId(int orderId);

}
