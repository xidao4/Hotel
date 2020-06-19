package com.example.hotel.blImpl.message;

import com.example.hotel.bl.message.MessageService;
import com.example.hotel.data.message.MessageMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Message;
import com.example.hotel.vo.MessageVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SendMessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-06-05
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final static String SEND_ERROR = "消息发送失败";
    private final static String UPDATE_ERROR = "状态更新失败";

    @Autowired
    MessageMapper messageMapper;
    @Autowired
    AccountMapper accountMapper;


    @Override
    public List<MessageVO> getQuesById(int toId) {
        return getMessageVOs(messageMapper.getQuesById(toId));
    }

    @Override
    public List<MessageVO> getQuesForClientById(int fromId) {
        return getMessageVOs(messageMapper.getQuesForClientById(fromId));
    }

    @Override
    public List<MessageVO> getChatById(int toId) {
        return getMessageVOs(messageMapper.getChatById(toId));
    }

    @Override
    public List<MessageVO> getPrivateAd(int toId) {
        return getMessageVOs(messageMapper.getPrivateAdById(toId));
    }

    @Override
    public List<MessageVO> getAdSentById(int fromId) {
        return getMessageVOs(messageMapper.getAdSentById(fromId));
    }

    @Override
    public List<MessageVO> getGroupMsg() {
        return getMessageVOs(messageMapper.getGroupMsg());
    }

    @Override
    public ResponseVO sendMessage(SendMessageVO sendMessageVO) {
        try{
            Message message = new Message();
            message.setTitle(sendMessageVO.getTitle());
            message.setContent(sendMessageVO.getContent());
            message.setFrom(sendMessageVO.getFrom());
            message.setType(sendMessageVO.getType());
            message.setStatus("0");
            message.setRetMsgId(sendMessageVO.getRetMsgId());
            for(int id: sendMessageVO.getTos()){
                message.setTo(id);
                messageMapper.insertMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure(SEND_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO changeMessageStatus(int id, String status) {
        try {
            messageMapper.changeMessageStatus(id, status);
        } catch (Exception e) {
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    private List<MessageVO> getMessageVOs(List<Message> messages) {
        ArrayList<MessageVO> messageVOS = new ArrayList<>();
        for(Message message: messages) {
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(message, messageVO);
            messageVO.setFromName(accountMapper.getUsernameById(messageVO.getFrom()));
            messageVO.setToName(accountMapper.getUsernameById(messageVO.getTo()));
            messageVO.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getSendTime()));
            messageVOS.add(messageVO);
        }
        return messageVOS;
    }
}
