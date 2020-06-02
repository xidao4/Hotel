package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.CreditUpdateStrategy;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditUpdateVO;
import com.example.hotel.vo.DefaultUpdateCreditVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: wyc
 * @Date: 2020-05-26
 */
public class DefaultCreditDecreImpl implements CreditUpdateStrategy {

    private DefaultUpdateCreditVO defaultUpdateCreditVO;
    private Double credit;


    @Autowired
    private OrderService orderService;

    /**
     * 如果撤销的订单距离最晚订单执行时间不足 6 个小时，扣除信用值为订单的总价值*1/2
     * @return
     */
    public CreditRecord getResult() {
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setUserId(defaultUpdateCreditVO.getUserId());
        creditRecord.setCredit(Math.max(0, credit - orderService.getPrice(defaultUpdateCreditVO.getOrderId()) * 0.5));
        creditRecord.setStatus("1");
        creditRecord.setDesc(defaultUpdateCreditVO.getDesc());
        return creditRecord;
    }

    @Override
    public void setCreditUpdateVO(CreditUpdateVO creditUpdateVO) {
        this.defaultUpdateCreditVO = (DefaultUpdateCreditVO) creditUpdateVO;
    }

    public void setUserCredit(Double credit) {
        this.credit = credit;
    }
}
