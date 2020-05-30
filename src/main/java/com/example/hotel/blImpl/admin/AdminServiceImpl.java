package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelAndManagerVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelService hotelService;

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

    /**
     * 网站管理员能看到的酒店和该酒店的工作人员，在同一行
     *
     * @return
     */
    @Override
    public List<HotelAndManagerVO> getHM() {
        List<HotelAndManagerVO> hms=new ArrayList<HotelAndManagerVO>();
        List<HotelVO> hs=hotelService.retrieveHotels();
        List<User> ms=this.getAllManagers();
        for(HotelVO h:hs){
            for(User m:ms){
                if(h.getManagerId()==m.getId()){
                    HotelAndManagerVO hm=new HotelAndManagerVO();
                    hm.setHotelId(h.getId());
                    hm.setName(h.getName());
                    hm.setManagerId(m.getId());
                    hm.setEmail(m.getEmail());
                    System.out.println(hm.getEmail());
                    hms.add(hm);
                }
            }
        }
        return hms;
    }

    @Override
    public List<User> getAllManagers() {
        return adminMapper.getAllManagers();
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


}
