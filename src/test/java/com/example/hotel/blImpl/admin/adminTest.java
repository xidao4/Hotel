package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    public void getAllClientTest(){
        System.out.println(adminService.getAllClients());
    }
    @Test
    public void getAllOperatorsTest(){
        System.out.println(adminService.getAllOperators());
    }
    @Test
    public void getAllManagersTest(){

        System.out.println(adminService.getAllManagers());
    }
}
