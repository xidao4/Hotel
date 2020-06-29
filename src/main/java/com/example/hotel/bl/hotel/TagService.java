package com.example.hotel.bl.hotel;

import com.example.hotel.po.MyTag;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;

import java.util.List;

public interface TagService {
    /**
     * 获取酒店设施服务标签
     * @param hotelId
     * @return
     */
    List<MyTag> getTagsByHotelId(Integer hotelId);

    /**
     * 增加
     * @param tagName
     * @param hotelId
     * @return
     */
    ResponseVO save(String tagName,Integer hotelId);

    /**
     * 删除
     * @param id
     * @return
     */
    ResponseVO deleteById(Integer id);
}
