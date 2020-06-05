package com.example.hotel.vo;


import java.util.List;

public class AdminCurveVO {
    private List<String> dateList;
    private List<Integer> numList;

    public AdminCurveVO(List<String> dateList, List<Integer> numList) {
        this.dateList = dateList;
        this.numList = numList;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<Integer> getNumList() {
        return numList;
    }

    public void setNumList(List<Integer> numList) {
        this.numList = numList;
    }
}
