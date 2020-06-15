package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.vo.HotelManagerVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    AccountService accountService;

    @PostMapping("/addOperator")
    public ResponseVO addOperator(@RequestBody UserForm userForm){
        return adminService.addOperator(userForm);
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @PostMapping("/getAllClients")
    public ResponseVO getAllClients(){
        return ResponseVO.buildSuccess(adminService.getAllClients());
    }

    @PostMapping("/getAllOperators")
    public ResponseVO getAllOperators(){return ResponseVO.buildSuccess(adminService.getAllOperators());}

    @PostMapping("/{id}/deleteUser")
    public ResponseVO deleteUser(@PathVariable int id ){
        return accountService.deleteUser(id);
    }

    @PostMapping("/{id}/tmpUserInfo/update")
    public ResponseVO updateTmpUserInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        //userInfoVO中的密码是输入的密码，或者""（表示没有改动过密码）
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/searchOO/{keyword}")
    public ResponseVO searchOOByKeyword(@PathVariable("keyword") String keyword){
        System.out.println("in the method");
        return ResponseVO.buildSuccess(adminService.searchOO(keyword));
    }

    @GetMapping("/searchClient/{keyword}")
    public ResponseVO searchClientByKeyword(@PathVariable("keyword") String keyword){
        return ResponseVO.buildSuccess(adminService.searchClient(keyword));
    }

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody HotelManagerVO hotelManagerVO){
        return adminService.addManager(hotelManagerVO);
    }

    @PostMapping("/{hotelId}/deleteHM")
    public ResponseVO deleteHM(@PathVariable Integer hotelId){
        return adminService.deleteHM(hotelId);
    }
}
