package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/{status}/changeStatus")
    public ResponseVO changeStatus(@PathVariable int orderid,@PathVariable String status){
        return orderService.changeStatus(orderid,status);
    }

    @GetMapping("/{orderid}/getOrder")
    public ResponseVO retrieveOrder(@PathVariable int orderid){
        return ResponseVO.buildSuccess(orderService.getOrder(orderid));
    }

    @GetMapping("/{orderid}/{reason}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid,@PathVariable String reason){
        System.out.println("OrderController");
        System.out.println(orderid);
        System.out.println(reason);
        return orderService.annulOrder(orderid,reason);
    }

    @GetMapping("/{hotelId}/allOrders")
    public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }

}
