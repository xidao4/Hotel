package com.example.hotel.blImpl.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ydl
 * @date 2020/06/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class orderTest {
    @Autowired
    OrderService orderService;

    @Test
    public void addOrder(){
        OrderVO orderVO=new OrderVO();
        orderVO.setWhetherComment(0);
        orderVO.setRoomType("BigBed");
        orderVO.setResidentName("ydl");
        orderVO.setClientName("测试号");
        orderVO.setCreateDate("2020-03-03");
        orderVO.setPhoneNumber("123");
        orderVO.setPeopleNum(2);
        orderVO.setHotelId(2);
        orderVO.setHotelName("如家酒店");
        orderVO.setHaveChild(true);
        orderVO.setUserId(4);
        orderVO.setPrice(100.0);
        orderVO.setRoomNum(1);
        orderVO.setCheckInDate("2020-04-01");
        orderVO.setCheckOutDate("2020-04-03");
        orderVO.setWhetherComment(0);
        orderService.addOrder(orderVO);

    }
    @Test
    public void getAllOrdersTest(){
        System.out.println(orderService.getAllOrders());
    }
    @Test
    public void getUserIdTest(){
        System.out.println(orderService.getUserId(5));
    }
}
