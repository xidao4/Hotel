package com.example.hotel.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static String getMD5(String plaintext) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesOfplaintext=plaintext.getBytes();
            md.update(bytesOfplaintext);
            byte[] digest=md.digest(bytesOfplaintext);//128位二进制，32位十六进制,
            BigInteger no=new BigInteger(1,digest);
            String hashText=no.toString(16);
            while(hashText.length()<32){
                hashText="0"+hashText;
            }
            return hashText;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "error";
    }
}
