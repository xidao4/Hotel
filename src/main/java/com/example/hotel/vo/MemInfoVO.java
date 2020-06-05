package com.example.hotel.vo;

import java.util.Date;

public class MemInfoVO {
    private Integer membership;
    private Integer memberPoints;
    private Date birthday;

    public Integer getMembership() {
        return membership;
    }

    public void setMembership(Integer membership) {
        this.membership = membership;
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
