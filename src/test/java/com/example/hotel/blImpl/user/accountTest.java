package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.enums.UserType;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
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

    @Test
    public void registerTest() {
        UserVO userVO = new UserVO();
        userVO.setCredit(100);
        userVO.setEmail("11@qq.com");
        userVO.setPassword("123456");
        userVO.setBirthday("2000-01-01");
        userVO.setUserName("测试一号");
        userVO.setPhoneNumber("1111111111");
        userVO.setUserType(UserType.Client);
        System.out.println(accountService.registerAccount(userVO));
    }

    @Test
    public void updateUserInfoTest() {
        System.out.println(accountService.updateUserInfo(4,"1234","测试1号","11111111"));
    }

    @Test
    public void updateAvatarTest() {
        System.out.println(accountService.updateAvatar("d959caadac9b13dcb3e609440135cf54"));
    }

}

