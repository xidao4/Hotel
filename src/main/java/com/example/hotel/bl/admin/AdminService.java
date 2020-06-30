package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;

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
    ResponseVO addManager(HotelManagerVO vo);


    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();
    List<User> getAllClients();
    List<User> getAllOperators();


    ResponseVO deleteHM(Integer hotelId);

    /**
     * 获得推广度曲线所需的datasets
     * @return
     */
    AdminCurveVO getCurveDataSet();


    /**
     * 运营获取包含会员信息的客户信息
     */
    List<OpUserVO> getAllClientsMen();
}
