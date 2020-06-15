package com.example.hotel.bl.credit;

import com.example.hotel.vo.*;

import java.util.List;

/**
 * @author wyc
 * @date 2020-05-26
 */
public interface CreditService {

    /**
     * 设置用户初始信用值
     * @param userId
     * @param Credit
     * @return
     */
    int initCredit(int userId, double Credit);

    ResponseVO defaultUpdateCredit(DefaultUpdateCreditVO defaultUpdateCreditVO, DefaultCreditUpdateStrategy defaultCreditUpdateStrategy);

    ResponseVO manualUpdateCredit(ManualUpdateCreditVO manualUpdateCreditVO);

    /**
     * 获取所有信用变更记录
     * @return
     */
    List<CreditRecordVO> getCreditRecords();

    /**
     * 根据用户id获取信用变更记录
     * @param userId
     * @return
     */
    List<CreditRecordVO> getUserCreditRecords(int userId);

    /**
     * 根据用户id获取用户信用值
     * @param userId
     * @return
     */
    Double getUserCredit(int userId);

    /**
     * 根据用户id获取信用曲线所需数据
     * @param userId
     * @return
     */
    CreditCurveVO getCreditCurve(int userId);

    /**
     * 将某条信用变更记录置为无效
     * @param id
     * @return
     */
    ResponseVO cancelCreditRecord(int id);

}
