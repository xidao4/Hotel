package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.TagService;
import com.example.hotel.data.hotel.MyTagMapper;
import com.example.hotel.po.MyTag;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    MyTagMapper myTagMapper;


    @Override
    public List<MyTag> getTagsByHotelId(Integer hotelId) {
        return myTagMapper.getTagsByHotelId(hotelId);
    }

    @Override
    public ResponseVO deleteById(Integer id) {
        return ResponseVO.buildSuccess(myTagMapper.deleteById(id));
    }

    @Override
    public ResponseVO save(String tagName,Integer hotelId) {
        if(myTagMapper.selectByHotelIdAndTagName(tagName,hotelId).size()!=0)//!=null
            return ResponseVO.buildFailure("您的酒店已有此服务标签！");
        MyTag tag=new MyTag();
        tag.setHotelId(hotelId);
        tag.setTagName(tagName);
        myTagMapper.insert(tag);
        return ResponseVO.buildSuccess(true);
    }

}
