package com.third.enterprise.controller;

import com.third.enterprise.bean.CheckOrder;
import com.third.enterprise.bean.request.CheckListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.ICheckOrderService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/operation/check")
public class CheckOrderController {

    private static final Logger logger = LoggerFactory.getLogger(CheckOrderController.class);

    @Autowired
    private ICheckOrderService checkOrderService;

    @PostMapping("/detail")
    public UnifiedResult checkDetail(Integer id){

        CheckOrder checkOrder = checkOrderService.selectById(id);
        if(checkOrder != null){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrder);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/history")
    public UnifiedResult checkHistory(@RequestBody CheckListRequest request){

        List<CheckOrder> checkOrderList = checkOrderService.checkHistory(request);
        if(checkOrderList != null && checkOrderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrderList,
                    Page.toPage(checkOrderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/wait")
    public UnifiedResult waitCheck(@RequestBody CheckListRequest request){
        List<CheckOrder> checkOrderList = checkOrderService.waitCheck(request);
        if(checkOrderList != null && checkOrderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrderList,
                    Page.toPage(checkOrderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/audit")
    public UnifiedResult auditCheck(@RequestBody CheckOrder checkOrder,
                                    @RequestAttribute("username")String username){
        checkOrder.setAuditor(username);
        if(checkOrderService.auditCheck(checkOrder)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/settle/list")
    public UnifiedResult settleList(CheckListRequest request){
        List<CheckOrder> checkOrderList = checkOrderService.settleList(request);
        if(checkOrderList != null && checkOrderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrderList,
                    Page.toPage(checkOrderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/settle")
    public UnifiedResult settleCheckOrder(Integer id,
                                          BigDecimal amount){

        CheckOrder checkOrder = checkOrderService.selectById(id);
        if(checkOrder != null && "2".equals(checkOrder.getStatus())){
            checkOrder.setAmount(amount);
            checkOrder.setStatus("4");
            if(checkOrderService.updateCheckOrder(checkOrder)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
            return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                    Constants.CALL_SERVICE_ERROR_MESSAGE);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
