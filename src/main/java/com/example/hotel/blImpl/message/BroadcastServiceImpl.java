package com.example.hotel.blImpl.message;

import com.example.hotel.bl.message.BroadcastService;
import com.example.hotel.data.message.BroadcastMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Broadcast;
import com.example.hotel.vo.BroadcastVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SendBroadcastVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-21
 */
@Service
public class BroadcastServiceImpl implements BroadcastService {

    private final static String UPDATE_P_ERROR = "优先级更新失败";
    private final static String UPDATE_S_ERROR = "状态更新失败";
    private final static String CREATE_ERROR = "公告创建失败";

    @Autowired
    BroadcastMapper broadcastMapper;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public List<BroadcastVO> getBroadcastList() {
        return getBroadcastVOs(broadcastMapper.getBroadcastList());
    }

    @Override
    public ResponseVO changeBroadcastStatus(int id, String status) {
        try {
            broadcastMapper.changeBroadcastStatus(id, status);
        } catch (Exception e) {
            return ResponseVO.buildFailure(UPDATE_S_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updatePriority(int id, int priority) {
        try {
            broadcastMapper.updatePriority(id, priority);
        } catch (Exception e) {
            return ResponseVO.buildFailure(UPDATE_P_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO sendBroadcast(SendBroadcastVO sendBroadcastVO) {
        try{
            Broadcast broadcast = new Broadcast();
            BeanUtils.copyProperties(sendBroadcastVO, broadcast);
            broadcastMapper.insertBroadcast(broadcast);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(CREATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    private List<BroadcastVO> getBroadcastVOs(List<Broadcast> broadcasts) {
        ArrayList<BroadcastVO> broadcastVOS = new ArrayList<>();
        for(Broadcast broadcast: broadcasts) {
            BroadcastVO broadcastVO = new BroadcastVO();
            BeanUtils.copyProperties(broadcast, broadcastVO);
            broadcastVO.setCreatedName(accountMapper.getUsernameById(broadcastVO.getCreatedId()));
            broadcastVO.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(broadcast.getCreatedTime()));
            broadcastVOS.add(broadcastVO);
        }
        return broadcastVOS;
    }
}
