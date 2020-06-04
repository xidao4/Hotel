package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.TagService;
import com.example.hotel.data.hotel.TagMapper;
import com.example.hotel.data.hotel.TagRelationMapper;
import com.example.hotel.po.Tag;
import com.example.hotel.po.TagRelation;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;
    @Autowired
    TagRelationMapper tagRelationMapper;

    @Override
    public List<TagVO> getTagsByHotelId(Integer hotelId) {
        List<TagVO> tagVOS=new ArrayList<>();
        List<TagRelation> tags=tagRelationMapper.selectTagsByHotelId(hotelId);
        for(TagRelation tagRelation:tags){
            String name=tagMapper.selectById(tagRelation.getTagId()).getTagName();
            TagVO tagVO=new TagVO();
            tagVO.setTagName(name);
            tagVO.setRelationId(tagRelation.getRelationId());
            tagVOS.add(tagVO);
        }
        return tagVOS;
    }

    @Override
    public ResponseVO save(String tagName,Integer hotelId) {
        if(tagMapper.selectByName(tagName)==null){
            Tag tag=new Tag();
            tag.setTagName(tagName);
            tagMapper.insert(tag);
        }
        TagRelation tagRelation=new TagRelation();
        tagRelation.setHotelId(hotelId);
        tagRelation.setHotelId(tagMapper.selectByName(tagName).getTagId());
        tagRelationMapper.insert(tagRelation);
        return null;
    }

    @Override
    public ResponseVO delete(Integer relationId) {
        return ResponseVO.buildSuccess(tagRelationMapper.delete(relationId));
    }
}
