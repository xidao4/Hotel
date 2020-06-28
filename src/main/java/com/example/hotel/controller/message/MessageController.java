package com.example.hotel.controller.message;

import com.example.hotel.bl.message.BroadcastService;
import com.example.hotel.bl.message.MessageService;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.SendBroadcastVO;
import com.example.hotel.vo.SendMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wyc
 * @Date: 2020-06-05
 */
@RestController()
@RequestMapping("/api/message")
public class MessageController {


    @Autowired
    MessageService messageService;

    @Autowired
    BroadcastService broadcastService;

    @GetMapping("/{id}/getQuesById")
    public ResponseVO getQuesById(@PathVariable int id){
        return ResponseVO.buildSuccess(messageService.getQuesById(id));
    }

    @GetMapping("/{id}/getQuesForClientById")
    public ResponseVO getQuesForClientById(@PathVariable int id){
        return ResponseVO.buildSuccess(messageService.getQuesForClientById(id));
    }

    @GetMapping("/{id}/getChatById")
    public ResponseVO getChatById(@PathVariable int id){
        return ResponseVO.buildSuccess(messageService.getChatById(id));
    }

    @GetMapping("/{id}/getPrivateAd")
    public ResponseVO getPrivateAd(@PathVariable int id){
        return ResponseVO.buildSuccess(messageService.getPrivateAd(id));
    }

    @GetMapping("/{id}/getAdSentById")
    public ResponseVO getAdSentById(@PathVariable int id){
        return ResponseVO.buildSuccess(messageService.getAdSentById(id));
    }

    @PostMapping("/sendMessage")
    public ResponseVO sendMessage(@RequestBody SendMessageVO sendMessageVO) {
        return messageService.sendMessage(sendMessageVO);
    }


    @GetMapping("/{id}/{status}/changeMessageStatus")
    public ResponseVO changeMessageStatus(@PathVariable int id, @PathVariable String status) {
        return messageService.changeMessageStatus(id, status);
    }

    @GetMapping("/getBroadcastList")
    public ResponseVO getBroadcastList(){
        System.out.println("啊啊啊啊啊啊，调用了broadcast");
        return ResponseVO.buildSuccess(broadcastService.getBroadcastList());
    }

    @PostMapping("/sendBroadcast")
    public ResponseVO sendBroadcast(@RequestBody SendBroadcastVO sendBroadcastVO) {
        return broadcastService.sendBroadcast(sendBroadcastVO);
    }

    @GetMapping("/{id}/{status}/changeBroadcastStatus")
    public ResponseVO changeBroadcastStatus(@PathVariable int id, @PathVariable String status) {
        return broadcastService.changeBroadcastStatus(id, status);
    }

    @GetMapping("/{id}/{priority}/updatePriority")
    public ResponseVO updatePriority(@PathVariable int id, @PathVariable int priority) {
        return broadcastService.updatePriority(id, priority);
    }
}
