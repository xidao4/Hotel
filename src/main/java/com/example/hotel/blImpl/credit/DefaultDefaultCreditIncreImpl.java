package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.CreditUpdateStrategy;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditUpdateVO;
import com.example.hotel.vo.DefaultUpdateCreditVO;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultCreditIncreImpl implements CreditUpdateStrategy {

    private DefaultUpdateCreditVO defaultUpdateCreditVO;
    private Double credit;

    @Autowired
    private OrderService orderService;

    @Override
    public CreditRecord getResult() {
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setUserId(defaultUpdateCreditVO.getUserId());
        creditRecord.setCredit(credit + orderService.getPrice(defaultUpdateCreditVO.getOrderId()));
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
