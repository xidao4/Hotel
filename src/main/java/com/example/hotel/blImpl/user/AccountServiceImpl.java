package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.MemberMapper;
import com.example.hotel.po.Member;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String  UPDATE_ERROR="更新失败";
    private final static String  DELETE_ERROR="删除失败";

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        try {
            accountMapper.createNewAccount(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }
    @Override
    public User login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }
    @Override
    public User getUserInfo(int id) {
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }
    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber) {
        try {
            accountMapper.updateAccount(id, password, username, phonenumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO deleteUser(int id){
        try {
            accountMapper.deleteUser(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerMem(MemRegisterVO memRegisterVO) {
        int userId=memRegisterVO.getUserId();
        List<Order> orderList=orderMapper.getUserOrders(userId);
        for(Order o:orderList){
            if(o.getOrderState()=="已完成"){
                Member member=new Member();

                try{
                    Date date=new SimpleDateFormat("yyyy-MM-dd").parse(memRegisterVO.getBirthday());
                    member.setBirthday(date);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return ResponseVO.buildFailure("生日输入格式错误，注册失败");
                }

                member.setUserId(userId);

                try{
                    memberMapper.insert(member);
                    return ResponseVO.buildSuccess(true);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return ResponseVO.buildFailure("注册会员发生错误，注册失败");
                }
            }
        }
        return ResponseVO.buildFailure("注册会员资格不足");
    }

    @Override
    public ResponseVO upgradeMem(int userId) {
        List<Order> orderList=orderMapper.getUserOrders(userId);
        int count=0;
        for(Order o:orderList) {
            if (o.getOrderState() == "已完成") {
                count++;
            }
            if(count==3){
                Member member = new Member();
                memberMapper.update(memberMapper.getInfo(userId).getMemberPoints(),userId);
                return ResponseVO.buildSuccess(true);
            }
        }
        return ResponseVO.buildFailure("没有升级金会员的资格");
    }

    @Override
    public MemInfoVO getMemInfo(int userId) {
        MemInfoVO memInfoVO=new MemInfoVO();
        Member member= memberMapper.getInfo(userId);
        if(member==null){
            return null;
        }
        memInfoVO.setBirthday(member.getBirthday());
        memInfoVO.setMemberPoints(member.getMemberPoints());
        memInfoVO.setMembership(member.getMembership());
        return memInfoVO;
    }
}
