package com.example.hotel.controller.hotel;

import com.alibaba.fastjson.JSON;
import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelInfoVO;
import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return hotelService.updateHotelInfo(id,hotelInfoVO.getAddress(),hotelInfoVO.getPhoneNum(),hotelInfoVO.getDescription());
    }
    @GetMapping("/updateRoom/{hotelId}/{roomType}/{curNum}/{total}/{price}")
    public ResponseVO updateRoom(@PathVariable int hotelId,@PathVariable String roomType,@PathVariable int curNum,@PathVariable int total,@PathVariable double price ){
        return roomService.updateRoom(hotelId,roomType,curNum,total,price);
    }

}
