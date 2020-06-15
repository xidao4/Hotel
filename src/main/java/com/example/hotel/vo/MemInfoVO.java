package com.example.hotel.vo;

import java.time.LocalDate;
import java.util.Date;

public class MemInfoVO {
    private Integer memberPoints;
    private LocalDate birthday;

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }



}
