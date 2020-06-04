package com.example.hotel.blImpl.credit;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.credit.DefaultCreditUpdateStrategy;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.credit.CreditMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyc
 * @date 2020-05-26
 */
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditMapper creditMapper;
    @Autowired
    private OrderMapper orderMapper;

    private final static String UPDATE_ERROR = "信用值更新失败";
    private final static String CANCEL_ERROR = "撤销失败";

    @Override
    public int initCredit(int userId, double credit) {
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setUserId(userId);
        creditRecord.setCredit(credit);
        creditRecord.setDesc("用户注册，信用值初始化");
        creditRecord.setStatus("1");
        return creditMapper.insertCreditRecord(creditRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO defaultUpdateCredit(DefaultUpdateCreditVO defaultUpdateCreditVO, DefaultCreditUpdateStrategy defaultCreditUpdateStrategy) {
        try {
            Integer userId = orderMapper.getUserId(defaultUpdateCreditVO.getOrderId());
            Double credit = creditMapper.getUserCredit(userId);
            Double price = orderMapper.getPriceById(defaultUpdateCreditVO.getOrderId());
            CreditRecord creditRecord = new CreditRecord();
            creditRecord.setUserId(userId);
            creditRecord.setCredit(defaultCreditUpdateStrategy.getResult(price, credit));
            creditRecord.setStatus("1");
            creditRecord.setDesc(defaultUpdateCreditVO.getDesc());
            creditMapper.insertCreditRecord(creditRecord);
            int crid = creditRecord.getRid();
            orderMapper.setCrid(defaultUpdateCreditVO.getOrderId(), crid);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO manualUpdateCredit(ManualUpdateCreditVO manualUpdateCreditVO) {
        try {
            Integer userId = orderMapper.getUserId(manualUpdateCreditVO.getOrderId());
            CreditRecord creditRecord = new CreditRecord();
            creditRecord.setUserId(userId);
            creditRecord.setCredit(manualUpdateCreditVO.getCreditVal());
            creditRecord.setStatus("1");
            creditRecord.setDesc(manualUpdateCreditVO.getDesc());
            creditMapper.insertCreditRecord(creditRecord);
            int crid = creditRecord.getRid();
            orderMapper.setCrid(manualUpdateCreditVO.getOrderId(), crid);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<CreditRecordVO> getCreditRecords() {
        List<CreditRecord> creditRecords= creditMapper.getCreditRecords();
        return copyList(creditRecords);
    }

    @Override
    public List<CreditRecordVO> getUserCreditRecords(int userId) {
        List<CreditRecord> creditRecords= creditMapper.getUserCreditRecords(userId);
        return copyList(creditRecords);
    }

    @Override
    public Double getUserCredit(int userId) {
        return creditMapper.getUserCredit(userId);
    }

    @Override
    public ResponseVO cancelCreditRecord(int id) {
        try {
            creditMapper.cancelCreditRecord(id);
        } catch (Exception e) {
            return ResponseVO.buildFailure(CANCEL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    private List<CreditRecordVO> copyList(List<CreditRecord> creditRecordList) {
        ArrayList<CreditRecordVO> creditRecordVOS = new ArrayList<>();
        for(CreditRecord creditRecord: creditRecordList) {
            CreditRecordVO creditRecordVO = new CreditRecordVO();
            creditRecordVO.setRid(creditRecord.getRid());
            creditRecordVO.setUserId(creditRecord.getUserId());
            creditRecordVO.setUsername(creditRecord.getUsername());
            creditRecordVO.setCredit(creditRecord.getCredit());
            creditRecordVO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creditRecord.getCreateTime()));
            creditRecordVO.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creditRecord.getUpdateTime()));
            creditRecordVO.setStatus(creditRecord.getStatus());
            creditRecordVO.setDesc(creditRecord.getDesc());
            creditRecordVOS.add(creditRecordVO);
        }
        return creditRecordVOS;
    }
}
