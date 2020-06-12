package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.TagService;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController()
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{hotelId}/list")
    public ResponseVO list(@PathVariable Integer hotelId){
        return ResponseVO.buildSuccess(tagService.getTagsByHotelId(hotelId));
    }


    @PostMapping("/{tagName}/{hotelId}/save")
    public ResponseVO save(@PathVariable("tagName") String tagName, @PathVariable("hotelId") Integer hotelId){
        return tagService.save(tagName,hotelId);
    }

    @PostMapping("/{id}/delete")
    public ResponseVO deleteByTagId(@PathVariable Integer id){
        return ResponseVO.buildSuccess(tagService.deleteById(id));
    }
}
