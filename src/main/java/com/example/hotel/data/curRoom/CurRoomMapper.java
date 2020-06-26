package com.example.hotel.data.curRoom;

import com.example.hotel.po.CurRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CurRoomMapper {
    int insertCurRoom(CurRoom curRoom);
    Integer isExist(@Param("hotelId") int hotelId,@Param("roomType") String roomType,@Param("daytime") String daytime);
    int updateCurRoomNum(@Param("hotelId") int hotelId,@Param("roomType") String roomType,@Param("daytime") String daytime,@Param("reserveNum") int reserveNum);
    int selectCurRoomNum(@Param("hotelId") int hotelId,@Param("roomType") String roomType,@Param("daytime") String daytime);
}
