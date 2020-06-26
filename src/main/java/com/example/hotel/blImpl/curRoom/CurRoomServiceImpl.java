package com.example.hotel.blImpl.curRoom;

import com.example.hotel.bl.curRoom.CurRoomService;
import com.example.hotel.data.curRoom.CurRoomMapper;
import com.example.hotel.po.CurRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurRoomServiceImpl implements CurRoomService {
    @Autowired
    CurRoomMapper curRoomMapper;

    @Override
    public int isExist(int hotelId,String roomType,String daytime){
        return curRoomMapper.isExist(hotelId,roomType,daytime);
    }
    @Override
    public int addCurRoom(CurRoom curRoom){
        curRoomMapper.insertCurRoom(curRoom);
        return 1;
    }
}
