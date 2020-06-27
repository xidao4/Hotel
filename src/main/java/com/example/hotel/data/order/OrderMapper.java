package com.example.hotel.data.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface OrderMapper {

    int addOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getOrderByHotelId(@Param("hotelId") int hotelId);

    List<Order> getUserOrders(@Param("userid") int userid);

    int annulOrder(@Param("orderid") int orderid,@Param("reason") String reason);

    Order getOrderById(@Param("orderid") int orderid);

    int changeStatus(@Param("orderid") int orderid,@Param("status") String status);

    Double getPriceById(@Param("orderid") int orderid);

    Integer getUserId(@Param("orderid") int orderid);

    Integer setCrid(@Param("orderid") int orderid, @Param("crid") int crid);

    void changeCommentStatus(@Param("orderId") int orderId);

}
