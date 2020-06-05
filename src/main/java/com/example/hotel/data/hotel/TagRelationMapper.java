package com.example.hotel.data.hotel;

import com.example.hotel.po.TagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagRelationMapper {
    int insert(TagRelation tagRelation);
    int delete(Integer relationId);
    List<TagRelation> selectTagsByHotelId(Integer hotelId);
}
