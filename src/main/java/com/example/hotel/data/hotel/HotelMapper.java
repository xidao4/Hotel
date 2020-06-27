package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.vo.HotelVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    int setManagerId(@Param("managerId")Integer managerId,@Param("id") Integer id);

    int delete(@Param("id") Integer id);

    int update(@Param("id") int id, @Param("address") String address,@Param("phoneNum") String phoneNum);

    int updatePic(@Param("id") int id, @Param("pic_1") String pic_1, @Param("pic_2") String pic_2, @Param("pic_3") String pic_3, @Param("pic_4") String pic_4, @Param("pic_5") String pic_5, @Param("pic_6") String pic_6, @Param("pic_7") String pic_7, @Param("pic_8") String pic_8);
}
