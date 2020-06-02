package com.example.hotel.bl.credit;

import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.CreditUpdateVO;

/**
 * @author wyc
 * @date 2020-05-26
 */
public interface CreditUpdateStrategy {

    public CreditRecord getResult();

    public void setCreditUpdateVO(CreditUpdateVO creditUpdateVO);

}
