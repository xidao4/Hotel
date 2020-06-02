package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.DefaultCreditUpdateStrategy;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditUpdateVO;
import com.example.hotel.vo.DefaultUpdateCreditVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: wyc
 * @Date: 2020-05-26
 */
public class DefaultDefaultCreditDecreImpl implements DefaultCreditUpdateStrategy {

    /**
     * 如果撤销的订单距离最晚订单执行时间不足 6 个小时，扣除信用值为订单的总价值*1/2
     * @return
     */
    @Override
    public Double getResult(Double price, Double credit) {
        return Math.max(0, credit - price * 0.5);
    }
}
