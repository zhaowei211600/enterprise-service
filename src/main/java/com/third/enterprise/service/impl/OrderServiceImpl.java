package com.third.enterprise.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.Order;
import com.third.enterprise.bean.request.OperationRoleRequest;
import com.third.enterprise.bean.request.OrderListRequest;
import com.third.enterprise.dao.OrderMapper;
import com.third.enterprise.service.IOrderService;
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

    @Override
    public Order selectById(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 核验列表
     * @param request
     * @return
     */
    @Override
    public List<Order> listCheck(OrderListRequest request) {

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return orderMapper.listCheck(request);
    }
}
