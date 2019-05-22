package com.third.enterprise.service;

import com.third.enterprise.bean.Order;
import com.third.enterprise.bean.request.OperationRoleRequest;
import com.third.enterprise.bean.request.OrderListRequest;

import java.util.List;

public interface IOrderService {

    List<Order> listOrder(OperationRoleRequest request);

    Order selectById(Integer orderId);

    List<Order> listCheck(OrderListRequest request);
}
