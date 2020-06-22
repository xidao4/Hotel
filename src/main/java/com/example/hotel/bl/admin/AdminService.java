package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.AdminCurveVO;
import com.example.hotel.vo.HotelManagerVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;

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

    List<User> searchOO(String keyword);
    List<User> searchClient(String keyword);

    ResponseVO deleteHM(Integer hotelId);

    /**
     * 获得推广度曲线所需的datasets
     * @return
     */
    AdminCurveVO getCurveDataSet();
}
