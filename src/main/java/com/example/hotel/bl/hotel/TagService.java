package com.example.hotel.bl.hotel;

import com.example.hotel.po.Tag;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;

import java.util.List;

public interface TagService {
    List<TagVO> getTagsByHotelId(Integer hotelId);
    ResponseVO save(String tagName,Integer hotelId);
    ResponseVO delete(Integer relationId);
}
