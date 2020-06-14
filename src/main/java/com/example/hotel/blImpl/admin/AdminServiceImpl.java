package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.User;
import com.example.hotel.vo.AdminCurveVO;
import com.example.hotel.vo.HotelManagerVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.sql.Types.NULL;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    HotelMapper hotelMapper;

    @Override
    public ResponseVO addOperator(UserForm userForm) {
        User user = new User();
        user.setEmail(userForm.getEmail());
        user.setUserName(userForm.getUserName());
        user.setPassword(userForm.getPassword());
        user.setUserType(UserType.Operator);
        try {
            adminMapper.addOperator(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addManager(HotelManagerVO vo) {
        //first add the hotelManager into the user table
        User user = new User();
        user.setEmail(vo.getEmail());
        user.setUserName(vo.getUserName());
        user.setPassword(vo.getPassword());
        user.setUserType(UserType.HotelManager);
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        //then update the hotel info(managerId)
        User manager=accountMapper.getAccountByName(vo.getEmail());
        hotelMapper.setManagerId(manager.getId(),vo.getHotelId());
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
        String date = clients.get(0).getCreateTime().toString().substring(0, 10);
        for (User user : clients) {
            if (user.getCreateTime().toString().substring(0, 10).equals(date))
                ++num;
            else {
                while (date.compareTo(user.getCreateTime().toString().substring(0, 10)) < 0) {
                    dates.add(date);
                    nums.add(num);
                    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString().substring(0, 10);
                }
                ++num;
            }
        }
        dates.add(date);
        nums.add(num);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(System.currentTimeMillis());
        while(date.compareTo(formatter.format(now)) <= 0) {
            dates.add(date);
            nums.add(num);
            date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1).toString().substring(0, 10);
        }
        return new AdminCurveVO(dates, nums);
    }

    @Override
    public List<User> getAllClients(){
        return adminMapper.getAllClients();
    }

    @Override
    public List<User> getAllOperators() { return adminMapper.getAllOperators(); }

    @Override
    public List<User> searchOO(String keyword) {
        return accountMapper.searchOO(keyword);
    }
    @Override
    public List<User> searchClient(String keyword) {
        return accountMapper.searchClient(keyword);
    }
    @Override
    public ResponseVO deleteHM(Integer hotelId){
        HotelVO hotelVO=hotelMapper.selectById(hotelId);
        hotelMapper.delete(hotelId);
        if(hotelVO.getManagerId()!=null){
            accountMapper.deleteUser(hotelVO.getManagerId());
        }
        return ResponseVO.buildSuccess(true);
//        try{
//            accountMapper.deleteUser(hotelVO.getManagerId());
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        hotelMapper.delete(hotelId);
//        return ResponseVO.buildSuccess(true);
    }
}
