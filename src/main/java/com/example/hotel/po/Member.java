package com.example.hotel.po;

import java.util.Date;

public class Member {
    private Integer userId;
    private Integer memberPoints;
    private Date birthday;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


}
