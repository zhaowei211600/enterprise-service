package com.third.enterprise.controller;

import com.third.enterprise.bean.Order;
import com.third.enterprise.bean.response.UnifiedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public UnifiedResult<List<Order>> findOrderByProductId(Integer productId){
        return null;
    }
}
