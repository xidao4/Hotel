package com.example.hotel.data.message;

import com.example.hotel.po.Broadcast;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-21
 */
@Mapper
@Repository
public interface BroadcastMapper {
    /**
     * 获取公告列表
     * @return
     */
    List<Broadcast> getBroadcastList();

    /**
     * 撤销公告
     * @param id
     */
    void changeBroadcastStatus(@Param("id") int id, @Param("status") String status);

    /**
     * 更改公告优先级
     * @param id
     * @param priority
     */
    void updatePriority(@Param("id") int id, @Param("priority") int priority);

    /**
     * 创建公告
     * @param broadcast
     * @return
     */
    Integer insertBroadcast(Broadcast broadcast);
}
