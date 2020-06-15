package com.example.hotel.controller.credit;

import com.example.hotel.bl.credit.CreditService;
import com.example.hotel.blImpl.credit.DefaultDefaultCreditDecreImpl;
import com.example.hotel.blImpl.credit.DefaultDefaultCreditIncreImpl;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{method}")
    public ResponseVO decrementDefault(@PathVariable String method, @RequestBody DefaultUpdateCreditVO defaultUpdateCreditVO){
        if(method.equals("IncreDefault")) {
            return creditService.defaultUpdateCredit(defaultUpdateCreditVO, new DefaultDefaultCreditIncreImpl());
        } else if(method.equals("decreDefault")) {
            return creditService.defaultUpdateCredit(defaultUpdateCreditVO, new DefaultDefaultCreditDecreImpl());
        }
        return ResponseVO.buildFailure("路径错误");
    }

    @PostMapping("/manualUpdate")
    public ResponseVO manualUpdate(@RequestBody ManualUpdateCreditVO manualUpdateCreditVO) {
        return creditService.manualUpdateCredit(manualUpdateCreditVO);
    }

    @GetMapping("/getCreditRecords")
    public ResponseVO getCreditRecords() {
        return ResponseVO.buildSuccess(creditService.getCreditRecords());
    }

    @GetMapping("/{id}/getUserCreditRecords")
    public ResponseVO getUserCredit(@PathVariable int id) {
        return ResponseVO.buildSuccess(creditService.getCreditCurve(id));
    }

}
