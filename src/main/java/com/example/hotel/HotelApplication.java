package com.example.hotel;

import com.example.hotel.util.MD5;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HotelApplication {

    public static void main(String[] args) {
        System.out.println("ILoveJava"+MD5.getMD5("ILoveJava"));
        System.out.println("ILoveCPP"+MD5.getMD5("ILoveCPP"));
        System.out.println("12345678"+MD5.getMD5("12345678"));
        System.out.println("12"+MD5.getMD5("12"));
        SpringApplication.run(HotelApplication.class, args);
    }

}
