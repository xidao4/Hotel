package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wyc
 * @date 2020-06-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class accountTest {

    @Autowired
    AccountService accountService;

    @Test
    public void loginTest() {
        UserForm userForm = new UserForm();
        userForm.setEmail("85@qq.com");
        userForm.setPassword("123456");
        System.out.println(accountService.login(userForm));
    }
}

