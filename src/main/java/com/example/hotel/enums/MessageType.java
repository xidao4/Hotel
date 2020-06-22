package com.example.hotel.enums;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */
public enum MessageType {
    Private("0"),
    Question("1");
    private String value;

    MessageType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }

}
