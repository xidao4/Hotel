package com.example.hotel.controller.user;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController()
@RequestMapping("/api/user")
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR = "用户名或密码错误";
    private final static String DELETE_ERROR="删除失败";
    private final static String CREDIT_INFO_ERROR = "信用值获取失败";

    @Autowired
    private AccountService accountService;
    @Autowired
    private CreditService creditService;

    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm) {
        User user = accountService.login(userForm);
        return getResUserVO(user);
    }

    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserVO userVO) {
        return accountService.registerAccount(userVO);
    }

    @GetMapping("/{id}/getUserInfo")
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = accountService.getUserInfo(id);
        return getResUserVO(user);
    }

    @PostMapping("/{id}/userInfo/update")
    public ResponseVO updateInfo(@RequestBody UserInfoVO userInfoVO,@PathVariable int id){
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/registerMem")
    public ResponseVO registerMem(@RequestBody MemRegisterVO memRegisterVO){
        return accountService.registerMem(memRegisterVO);
    }

    @PostMapping("/{userId}/upgradeMem")
    public ResponseVO upgradeMem(@PathVariable int userId){
        return accountService.upgradeMem(userId);
    }

    @GetMapping("/{userId}/getMemInfo")
    public ResponseVO getMemInfo(@PathVariable int userId){
        System.out.println("into the method back end");
        MemInfoVO memInfoVO=accountService.getMemInfo(userId);
        if(memInfoVO==null){
            return ResponseVO.buildSuccess(false);
        }
        return ResponseVO.buildSuccess(memInfoVO);
    }

    //TODO: 头像相关

    private ResponseVO getResUserVO(User user) {
        if(user == null) {
            return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        if(user.getUserType().equals(UserType.Client)){
            Double credit = creditService.getUserCredit(user.getId());
            if(credit == null){
                return ResponseVO.buildFailure(CREDIT_INFO_ERROR);
            }
            userVO.setCredit(credit);
        }
        return ResponseVO.buildSuccess(userVO);
    }
}
