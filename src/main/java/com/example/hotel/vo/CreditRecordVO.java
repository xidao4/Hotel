package com.example.hotel.vo;

/**
 * @Author: wyc
 * @Date: 2020-05-26
 */
public class CreditRecordVO {

    private Integer rid;
    private Integer userId;
    private String username;
    private Double credit;
    private String createTime;
    private String updateTime;
    private String status;
    private String desc;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "CreditRecordVO{" +
                "rid=" + rid +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", credit=" + credit +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
