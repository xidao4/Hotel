package com.example.hotel.bl.hotel;


import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CommentService {

    ResponseVO addComment(CommentVO commentVO);

    List<Comment> getCommentByHotelId(Integer hotelId);

    ResponseVO updateReply(int commentId,String reply);
}
