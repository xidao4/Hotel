package com.example.hotel.vo;

public class CreditUpdateVO {

    private Integer userId;
    private Double creditVal;
    private String desc;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getCreditVal() {
        return creditVal;
    }

    public void setCreditVal(Double creditVal) {
        this.creditVal = creditVal;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
