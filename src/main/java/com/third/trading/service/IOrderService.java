package com.third.trading.service;

import com.third.trading.bean.Order;
import com.third.trading.bean.request.OperationRoleRequest;

import java.util.List;

public interface IOrderService {

    List<Order> listOrder(OperationRoleRequest request);
}
