package com.example.hotel.data.hotel;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ydl
 */
@Mapper
@Repository
public interface CommentMapper {
    int addComment(Comment comment);

    List<Comment> getCommentByHotelId(@Param("hotelId") Integer hotelId);

    int updateReply(@Param("commentId") int commentId,@Param("reply") String reply);
}
