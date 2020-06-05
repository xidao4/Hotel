package com.example.hotel.data.hotel;

import com.example.hotel.po.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface TagMapper {
    int insert(Tag tag);
    Tag selectById(Integer tagId);
    Tag selectByName(String tagName);
}
