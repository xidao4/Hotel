package com.example.hotel.bl.hotel;

import com.example.hotel.po.MyTag;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;

import java.util.List;

public interface TagService {
    List<MyTag> getTagsByHotelId(Integer hotelId);
    ResponseVO save(String tagName,Integer hotelId);
    ResponseVO deleteById(Integer id);
}
