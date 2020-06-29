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
        tagService.save("健身房",1);
    }
    @Test
    public void list(){
        tagService.getTagsByHotelId(2);
    }
}
