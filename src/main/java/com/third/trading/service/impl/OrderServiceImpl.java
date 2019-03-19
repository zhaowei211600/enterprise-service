package com.third.trading.service.impl;

import com.third.trading.bean.Order;
import com.third.trading.bean.request.OperationRoleRequest;
import com.third.trading.dao.OrderMapper;
import com.third.trading.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements IOrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> listOrder(OperationRoleRequest request) {
        return orderMapper.listOrder(request);
    }
}
