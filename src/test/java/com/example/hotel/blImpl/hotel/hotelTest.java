package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author syc
 * @date 2020/06/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class hotelTest {

    @Autowired
    HotelService hotelService;

    @Test
    public void getAllHotelsTest() {
        System.out.println(hotelService.getAllHotels());
    }

    @Test
    public void retrieveHotelDetails() {
        System.out.println(hotelService.retrieveHotelDetails(1,4));
    }

}
