package com.example.hotel.vo;

import java.util.List;

public class CreditCurveVO {
    private List<String> dateList;
    private List<Integer> creditList;

    public CreditCurveVO(List<String> dateList, List<Integer> creditList) {
        this.dateList = dateList;
        this.creditList = creditList;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Integer> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<Integer> creditList) {
        this.creditList = creditList;
    }
}
