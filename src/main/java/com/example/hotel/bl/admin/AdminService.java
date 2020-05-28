package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加网站运营人员账号
     * @param userForm
     * @return
     */
    ResponseVO addOperator(UserForm userForm);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    List<User> getAllClients();

    List<User> getAllOperators();


}
