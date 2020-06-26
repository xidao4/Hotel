package com.example.hotel.controller.user;

import com.aliyun.oss.OSSClient;
import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.util.MD5;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;


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
       //userInfoVO中的密码是输入的密码，或者""（表示没有改动过密码）
        return accountService.updateUserInfo(id,userInfoVO.getPassword(),userInfoVO.getUserName(),userInfoVO.getPhoneNumber());
    }

    @PostMapping("/registerMem")
    public ResponseVO registerMem(@RequestBody MemRegisterVO memRegisterVO){
        return accountService.registerMem(memRegisterVO);
    }

    @GetMapping("/{userId}/getMemInfo")
    public ResponseVO getMemInfo(@PathVariable int userId){
        MemInfoVO memInfoVO=accountService.getMemInfo(userId);
        if(memInfoVO==null){
            return ResponseVO.buildSuccess(false);
        }
        return ResponseVO.buildSuccess(memInfoVO);
    }

    @PostMapping("/increaseMemberPoints")
    public ResponseVO increaseMemberPoints(@RequestBody MemUpdateVO memUpdateVO){
        return ResponseVO.buildSuccess(accountService.increaseMemberPoints(memUpdateVO.getUserId(),memUpdateVO.getMemberPoints()));
    }
    @PostMapping("/decreaseMemberPoints")
    public ResponseVO decreaseMemberPoints(@RequestBody MemUpdateVO memUpdateVO){
        return ResponseVO.buildSuccess(accountService.decreaseMemberPoints(memUpdateVO.getUserId(),memUpdateVO.getMemberPoints()));
    }

    @PostMapping("/upload")
    public ResponseVO uploadImage(@RequestParam(value="img") MultipartFile file) {
        //System.out.println(file);
        String type = file.getContentType().substring(6);
        //System.out.println(type);
        String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
        String ACCESSKEYID = "LTAI4Fo8mZ4975JwdZex9WM1";
        String ACCESSKEYSECRET = "8L5ESi5AUW5hwq6yeZ2LSaeBUeirGu";
        String BUCKETNAME = "supernatural";
        try {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + '.' + type;
            InputStream input = file.getInputStream();
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            // 上传文件流
            ossClient.putObject(BUCKETNAME, fileName, input);
            ossClient.shutdown();
            System.out.println(("图片上传阿里云 name=" + fileName));
            return accountService.updateAvatar(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("failure!");
        }
    }

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
