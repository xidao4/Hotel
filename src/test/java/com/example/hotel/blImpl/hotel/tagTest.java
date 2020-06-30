package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ljy
 * @date 06-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class tagTest {
    @Autowired
    TagService tagService;
    @Test
    public void save(){
        System.out.println(tagService.save("豪华客房",1));
    }
    @Test
    public void list(){
        System.out.println(tagService.getTagsByHotelId(1));
    }
}
