package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password,String username,String phonenumber);

    ResponseVO deleteUser(int id);

    ResponseVO registerMem(MemRegisterVO memRegisterVO);

    ResponseVO increaseMemberPoints(int userId, int amount);
    ResponseVO decreaseMemberPoints(int userId, int amount);

    MemInfoVO getMemInfo(int userId);

    /**
     * 更新头像信息
     * @param fileName
     * @return
     */
    ResponseVO updateAvatar(String fileName);
}
