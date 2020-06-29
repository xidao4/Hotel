package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ljy
 * @date 06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class adminTest {
    @Autowired
    AdminService adminService;
    @Autowired
    AccountService accountService;
    @Test
    public void modifyUserInfoTest(){
        accountService.updateUserInfo(4,"","测试一号","13861300984");
    }
    @Test
    public void getAllOperatorsTest(){
        adminService.getAllOperators();
    }
    @Test
    public void getAllManagersTest(){
        adminService.getAllManagers();
    }
}
