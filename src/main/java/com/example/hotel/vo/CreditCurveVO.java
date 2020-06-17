package com.example.hotel.vo;

import java.util.List;

public class CreditCurveVO {
    private List<String> dateList;
    private List<Double> creditList;

    public CreditCurveVO(List<String> dateList, List<Double> creditList) {
        this.dateList = dateList;
        this.creditList = creditList;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Double> getCreditList() {
        return creditList;
    }

    public void setCreditList(List<Double> creditList) {
        this.creditList = creditList;
    }
}
