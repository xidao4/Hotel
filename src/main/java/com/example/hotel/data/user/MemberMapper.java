package com.example.hotel.data.user;

import com.example.hotel.po.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface MemberMapper {
    int insert(Member member);
    Member getInfo(@Param("userId")int userId);
    int update(@Param("memberPoints") int memberPoints,@Param("userId") int userId);
}
