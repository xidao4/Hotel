package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.CommentService;
import com.example.hotel.data.hotel.CommentMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public ResponseVO addComment(CommentVO commentVO){
        Comment comment=new Comment();
        int orderId=commentVO.getOrderId();
        BeanUtils.copyProperties(commentVO,comment);
        commentMapper.addComment(comment);
        orderMapper.changeCommentStatus(orderId);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Comment> getCommentByHotelId(Integer hotelId){
        return commentMapper.getCommentByHotelId(hotelId);
    }

    @Override
    public ResponseVO updateReply(int commentId,String reply){
        commentMapper.updateReply(commentId,reply);
        return ResponseVO.buildSuccess(true);
    }
}
