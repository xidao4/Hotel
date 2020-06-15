package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.DefaultCreditUpdateStrategy;

/**
 * @Author: wyc
 * @Date: 2020-06-01
 */
public class DefaultDefaultCreditIncreImpl implements DefaultCreditUpdateStrategy {

    /**
     * 如果订单正常执行，信用值增加订单的总价值
     * @return
     */
    @Override
    public Double getResult(Double price, Double credit) {
        return price + credit;
    }
}
