package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.AdminCurveVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    AdminMapper adminMapper;
    @Override
    public ResponseVO addManager(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.HotelManager);
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
    }

    @Override
    public AdminCurveVO getCurveDataSet() {
        List<String> dates = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        List<User> clients = adminMapper.getAllClients();
        Collections.sort(clients, Comparator.comparing(u -> u.getCreateTime().toString()));
        Integer num = 0;
        String date = clients.get(0).getCreateTime().toString().substring(0,10);
        for(User user: clients){
            if(user.getCreateTime().toString().substring(0,10).equals(date))
                ++ num;
            else{
                while(date.compareTo(user.getCreateTime().toString().substring(0,10))<0){
                    dates.add(date);
                    nums.add(num);
                    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString().substring(0,10);
                }
                ++ num;
            }
        }
        dates.add(date);
        nums.add(num);
        return new AdminCurveVO(dates, nums);
    }
}
