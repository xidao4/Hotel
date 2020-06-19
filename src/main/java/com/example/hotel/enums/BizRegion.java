package com.example.hotel.enums;

public enum BizRegion {
    XiDan("西单"),
    GuLou("鼓楼"),
    XianLin("仙林");

    private String value;

    BizRegion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
