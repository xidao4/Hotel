package com.example.hotel.blImpl.credit;

import ch.qos.logback.classic.gaffer.PropertyUtil;
import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.bl.credit.CreditUpdateStrategy;
import com.example.hotel.data.credit.CreditMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final static String UPDATE_ERROR = "信用值更新失败";
    private final static String CANCEL_ERROR = "撤销失败";

    @Override
    public int initCredit(int userId, double credit) {
        try {
            CreditRecord creditRecord = new CreditRecord();
            creditRecord.setUserId(userId);
            creditRecord.setCredit(credit);
            creditRecord.setDesc("用户注册，信用值初始化");
            creditRecord.setStatus("1");
            creditMapper.insertCreditRecord(creditRecord);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public ResponseVO updateCredit(CreditUpdateStrategy creditUpdateStrategy) {
        try {
            CreditRecord creditRecord = creditUpdateStrategy.getResult();
            creditMapper.insertCreditRecord(creditRecord);
        } catch (Exception e) {
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess();
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
        return ResponseVO.buildSuccess();
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
