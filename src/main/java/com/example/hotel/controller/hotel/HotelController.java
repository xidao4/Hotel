package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelInfoVO;
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


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {
        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
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
}
