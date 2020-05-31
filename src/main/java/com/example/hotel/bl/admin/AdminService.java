package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.HotelAndManagerVO;
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
     * 网站管理员能看到的酒店和该酒店的工作人员，在同一行
     * @return
     */
    List<HotelAndManagerVO> getHM();

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    List<User> getAllClients();

    List<User> getAllOperators();

    List<User> searchOO(String keyword);
    List<User> searchClient(String keyword);
}
