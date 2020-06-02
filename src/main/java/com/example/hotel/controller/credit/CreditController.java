package com.example.hotel.controller.credit;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.blImpl.credit.DefaultCreditDecreImpl;
import com.example.hotel.blImpl.credit.DefaultCreditIncreImpl;
import com.example.hotel.blImpl.credit.ManualCreditUpdateImpl;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: wyc
 * @Date: 2020-05-26
 */
@RestController()
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    CreditService creditService;

    @GetMapping("/{id}/getCreditById")
    public ResponseVO getCreditById(@PathVariable int id){
        CreditVO creditVO = new CreditVO();
        creditVO.setCredit(creditService.getUserCredit(id));
        return ResponseVO.buildSuccess(creditVO);
    }

    @PostMapping("decreDefault")
    public ResponseVO decrementDefault(@RequestBody DefaultUpdateCreditVO defaultUpdateCreditVO){
        DefaultCreditDecreImpl defaultCreditDecre = new DefaultCreditDecreImpl();
        defaultCreditDecre.setCreditUpdateVO(defaultUpdateCreditVO);
        return creditService.updateCredit(defaultCreditDecre);
    }

    @PostMapping("increDefault")
    public ResponseVO incrementDefault(@RequestBody DefaultUpdateCreditVO defaultUpdateCreditVO){
        DefaultCreditIncreImpl defaultCreditIncre = new DefaultCreditIncreImpl();
        defaultCreditIncre.setCreditUpdateVO(defaultUpdateCreditVO);
        return creditService.updateCredit(defaultCreditIncre);
    }

    @PostMapping("decreManual")
    public ResponseVO manualUpdate(@RequestBody ManualUpdateCreditVO manualUpdateCreditVO){
        ManualCreditUpdateImpl manualCreditUpdate = new ManualCreditUpdateImpl();
        manualCreditUpdate.setCreditUpdateVO(manualUpdateCreditVO);
        return creditService.updateCredit(manualCreditUpdate);
    }

    @GetMapping("/getCreditRecords")
    public ResponseVO getCreditRecords() {
        return ResponseVO.buildSuccess(creditService.getCreditRecords());
    }

    @GetMapping("/{id}/getUserCreditRecords")
    public ResponseVO getUserCredit(@PathVariable int id) {
        return ResponseVO.buildSuccess(creditService.getUserCreditRecords(id));
    }

}
