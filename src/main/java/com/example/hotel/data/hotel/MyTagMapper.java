package com.example.hotel.data.hotel;

import com.example.hotel.po.MyTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MyTagMapper {
    List<MyTag> getTagsByHotelId(int hotel);
    int deleteById(int id);
    int insert(MyTag myTag);
}
