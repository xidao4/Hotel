package com.example.hotel.data.credit;

import com.example.hotel.po.CreditRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-05-26
 */
@Mapper
@Repository
public interface CreditMapper {

    /**
     * 根据用户id获取信用变更记录
     * @param userId
     * @return
     */
    List<CreditRecord> getUserCreditRecords(@Param("userId") int userId);

    /**
     * 获取所有信用变更记录
     * @return 信用变更记录列表
     */
    List<CreditRecord> getCreditRecords();

    /**
     * 根据用户id获取用户信用值
     * @param userId
     * @return
     */
    Double getUserCredit(@Param("userId") int userId);

    /**
     * 将某条信用变更记录置为无效
     * @param id
     * @return
     */
    int cancelCreditRecord(@Param("id") int id);

    /**
     * 插入信用变更记录
     * @param creditRecord
     * @return
     */
    int insertCreditRecord(CreditRecord creditRecord);

}
