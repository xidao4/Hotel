package com.example.hotel.bl.curRoom;

import com.example.hotel.po.CurRoom;

public interface CurRoomService {
    int isExist(int hotelId,String roomType,String daytime);
    int addCurRoom(CurRoom curRoom);
}
