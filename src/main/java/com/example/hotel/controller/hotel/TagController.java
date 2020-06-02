package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.TagService;
import com.example.hotel.po.TagRelation;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{hotelId}/list")
    public ResponseVO list(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(tagService.getTagsByHotelId(hotelId));
    }


    @PostMapping("/{tagName}/save")
    public ResponseVO save(@PathVariable String tagName){
        return tagService.save(tagName);
    }

    @PostMapping("/{relationId}/delete")
    public ResponseVO deleteByTagId(@PathVariable Integer relationId){
        return tagService.delete(relationId);
    }
}
