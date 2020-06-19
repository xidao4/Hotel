package com.example.hotel.data.message;

import com.example.hotel.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    /**
     * 根据客服id获取收到的问题
     * @return
     */
    List<Message> getQuesById(@Param("toId") int toId);

    /**
     * 根据客户id获取问过的问题
     * @param fromId
     * @return
     */
    List<Message> getQuesForClientById(@Param("fromId") int fromId);

    /**
     * 根据问题id获取聊天内容
     * @param id
     * @return
     */
    List<Message> getChatById(@Param("id") int id);

    /**
     * 根据id获取客服私发推送
     * @return
     */
    List<Message> getPrivateAdById(@Param("toId") int toId);

    /**
     * 根据id获取已发推送
     *      针对客服
     * @return
     */
    List<Message> getAdSentById(@Param("fromId") int fromId);

    /**
     * 获取所有公告
     * @return
     */
    List<Message> getGroupMsg();

    /**
     * 增加聊天记录（非IM）
     * @param message
     * @return
     */
    Integer insertMessage(Message message);

    /**
     * 更新消息状态
     * @param id
     * @param status
     */
    void changeMessageStatus(@Param("id") int id, @Param("status") String status);

}
