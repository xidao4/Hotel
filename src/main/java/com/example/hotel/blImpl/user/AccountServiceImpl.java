package com.example.hotel.blImpl.user;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.data.user.MemberMapper;
import com.example.hotel.po.Member;
import com.example.hotel.po.Order;
import com.example.hotel.data.credit.CreditMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
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
    private final static String INIT_ERROR = "账户信用值初始化失败";

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private CreditService creditService;

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
        if(creditService.initCredit(user.getId(), userVO.getCredit()) > 0) {
            return ResponseVO.buildSuccess();
        } else {
            return ResponseVO.buildFailure(INIT_ERROR);
        }
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
            System.out.println("订单编号"+o.getId()+" 客户编号"+o.getUserId()+" "+o.getOrderState());
        }
        for(Order o:orderList){
            if(o.getOrderState().equals("已执行")){
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
    public ResponseVO updateMemInfo(int userId, double amount) {
        try{
            memberMapper.update(amount*3,userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure("更新会员积分失败");
        }
        return ResponseVO.buildSuccess(true);
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
        return memInfoVO;
    }
}
