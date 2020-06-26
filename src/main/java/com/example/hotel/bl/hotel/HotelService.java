package com.example.hotel.bl.hotel;

import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelPicVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws ServiceException
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels(Integer userid);

    List<HotelVO> getAllHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId, Integer userid);

    HotelVO getHotelInfo(Integer hotelId);
    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    /**
     * 根据日期返回酒店
     * @param userid
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    List<HotelVO> retrieveHotelByDate(Integer userid, String checkInDate, String checkOutDate);

    int getTotalRoomNum(Integer hotelId);

   /* int getRoomNumOfTypicalType(HotelVO hotelVO, String roomType);*/

    ResponseVO getManagerId(Integer hotelId);

    int getHotelId(Integer managerId);

    ResponseVO updateHotelInfo(int id,String address,String phoneNum,String description);

    ResponseVO changePic(List<HotelPicVO> hotelPicVOList, int hotelId);

    ResponseVO getPics(int hotelId);

}
