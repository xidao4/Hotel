package com.example.hotel.bl.message;

import com.example.hotel.vo.BroadcastVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SendBroadcastVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-21
 */
public interface BroadcastService {

    /**
     * 获取公告列表
     * @return
     */
    List<BroadcastVO> getBroadcastList();

    /**
     * 撤销公告
     * @param id
     */
    ResponseVO changeBroadcastStatus(int id, String status);

    /**
     * 更改公告优先级
     * @param id
     * @param priority
     */
    ResponseVO updatePriority(int id, int priority);

    /**
     * 创建公告
     * @param sendBroadcastVO
     * @return
     */
    ResponseVO sendBroadcast(SendBroadcastVO sendBroadcastVO);
}
