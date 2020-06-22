package com.example.hotel.bl.message;

import com.example.hotel.vo.MessageVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SendMessageVO;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-05
 */

public interface MessageService {

    /**
     * 根据客服id获取收到的问题
     * @return
     */
    List<MessageVO> getQuesById(int toId);

    /**
     * 根据用户id获取提过的问题
     * @param fromId
     * @return
     */
    List<MessageVO> getQuesForClientById(int fromId);

    /**
     * 根据问题id获取聊天内容
     * @param id
     * @return
     */
    List<MessageVO> getChatById(int id);

    /**
     * 获取id客服私发推送
     *      客服查看记录
     *      客户接收消息
     * @return
     */
    List<MessageVO> getPrivateAd(int toId);

    /**
     * 根据id获取已发推送
     *      针对客服
     * @return
     */
    List<MessageVO> getAdSentById(int fromId);

    /**
     * 获取所有公告
     * 可能会修改！！！！！！
     * @return
     */
    List<MessageVO> getGroupMsg();

    /**
     * 客户与客服之间疑问与解答（非IM）
     * @param sendMessageVO
     * @return
     */
    ResponseVO sendMessage(SendMessageVO sendMessageVO);

    /**
     * 更新消息状态
     * @param id
     * @param status
     * @return
     */
    ResponseVO changeMessageStatus(int id, String status);

}
