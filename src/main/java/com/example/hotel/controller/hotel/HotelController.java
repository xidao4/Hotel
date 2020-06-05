package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
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
    private CommentService commentService;

    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
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

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess();
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

}
