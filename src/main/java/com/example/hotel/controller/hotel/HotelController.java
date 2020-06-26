package com.example.hotel.controller.hotel;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/all")
    public ResponseVO retrieveAllHotels(@RequestBody String str){
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        return ResponseVO.buildSuccess(hotelService.retrieveHotels(userId));
    }

    @GetMapping("/allHotels")
    public ResponseVO getAllHotels() {
        return ResponseVO.buildSuccess(hotelService.getAllHotels());
    }
    /**
     * 添加comment
     * @author ydl
     * @param commentVO
     * @return
     * @throws ServiceException
     */
    @PostMapping("/addComment")
    public ResponseVO createComment(@RequestBody CommentVO commentVO) throws ServiceException {
        System.out.println("$$$$$$$$$$$");
        System.out.println(commentVO);
        commentService.addComment(commentVO);
        return ResponseVO.buildSuccess(true);
    }

    /**
     * 根据酒店名称获取对应的所有评价
     * @author ydl
     * @param hotelId
     * @return
     */
    @GetMapping("/{hotelId}/getCommentByHotelId")
    public ResponseVO getCommentByHotelId(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(commentService.getCommentByHotelId(hotelId));
    }

    /**
     * 根据评价id来更新对应的酒店管理员reply
     * @return
     */
    @GetMapping("/{commentId}/{reply}/updateReply")
    public ResponseVO updateReply(@PathVariable int commentId,@PathVariable String reply){
        return commentService.updateReply(commentId,reply);
    }



    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        System.out.println("QQQQQ");
        System.out.println(hotelRoom);
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    @PostMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@RequestBody String str) {
        Integer hotelId = Integer.parseInt(JSON.parseObject(str).get("hotelId").toString());
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId,userId));
    }
    @GetMapping("/{currentHotelId}/currentHotelInfo")
    public ResponseVO getCurrentHotelInfo(@PathVariable int currentHotelId){
        return ResponseVO.buildSuccess(hotelService.getHotelInfo(currentHotelId));
    }

    @PostMapping("/inDateRange")
    public ResponseVO retrieveAllHotelsInDateRange(@RequestBody String str){
        System.out.println(str);
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        String checkInDate = JSON.parseObject(str).get("checkInDate").toString();
        String checkOutDate = JSON.parseObject(str).get("checkOutDate").toString();
        return ResponseVO.buildSuccess(hotelService.retrieveHotelByDate(userId,checkInDate,checkOutDate));
    }


    @PostMapping("/{hotelId}/getManagerId")
    public ResponseVO getManagerId(@PathVariable Integer hotelId){
        return hotelService.getManagerId(hotelId);
    }

    @PostMapping("/{managerId}/getHotelId")
    public ResponseVO getHotelId(@PathVariable Integer managerId){
        return ResponseVO.buildSuccess(hotelService.getHotelId(managerId));
    }

    @PostMapping("/{id}/hotelInfo/update")
    public ResponseVO updateInfo(@RequestBody HotelInfoVO hotelInfoVO, @PathVariable int id){
        return hotelService.updateHotelInfo(id,hotelInfoVO.getAddress(),hotelInfoVO.getPhoneNum());
    }
    @GetMapping("/updateRoom/{hotelId}/{roomType}/{curNum}/{total}/{price}")
    public ResponseVO updateRoom(@PathVariable int hotelId,@PathVariable String roomType,@PathVariable int curNum,@PathVariable int total,@PathVariable double price ){
        return roomService.updateRoom(hotelId,roomType,curNum,total,price);
    }

    @PostMapping("/{hotelId}/changePic")
    public ResponseVO changePic(@PathVariable int hotelId, @RequestBody List<HotelPicVO> hotelPicVOList){
        return hotelService.changePic(hotelPicVOList,hotelId);
    }

    @PostMapping("/upload")
    public ResponseVO uploadImage(@RequestParam(value="img") MultipartFile file) {
        //System.out.println(file);
        String type = file.getContentType().substring(6);
        //System.out.println(type);
        String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
        String ACCESSKEYID = "LTAI4Fo8mZ4975JwdZex9WM1";
        String ACCESSKEYSECRET = "8L5ESi5AUW5hwq6yeZ2LSaeBUeirGu";
        String BUCKETNAME = "supernatural";
        try {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + '.' + type;
            InputStream input = file.getInputStream();
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
            // 上传文件流
            ossClient.putObject(BUCKETNAME, fileName, input);
            ossClient.shutdown();
            System.out.println(("图片上传阿里云 name=" + fileName));
            return ResponseVO.buildSuccess(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("failure!");
        }
    }

    @GetMapping("/{hotelId}/getPics")
    public ResponseVO getPics(@PathVariable int hotelId){
        return hotelService.getPics(hotelId);
    }

}
