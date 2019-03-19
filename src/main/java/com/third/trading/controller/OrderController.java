package com.third.trading.controller;

import com.github.pagehelper.PageHelper;
import com.third.trading.bean.Order;
import com.third.trading.bean.request.OperationRoleRequest;
import com.third.trading.bean.response.UnifiedResult;
import com.third.trading.bean.response.UnifiedResultBuilder;
import com.third.trading.service.IOrderService;
import com.third.trading.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/operation/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private IOrderService orderService;

    @RequestMapping("/product")
    public UnifiedResult<List<Order>> findOrderByProductId(OperationRoleRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Order> orderList = orderService.listOrder(request);
        if(orderList != null && orderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, orderList);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/user")
    public UnifiedResult userOrder(Integer productId){

        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
