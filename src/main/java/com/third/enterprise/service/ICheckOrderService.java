package com.third.enterprise.service;

import com.third.enterprise.bean.CheckOrder;
import com.third.enterprise.bean.request.CheckListRequest;

import java.util.List;

public interface ICheckOrderService {

    List<CheckOrder> checkHistory(CheckListRequest request);

    List<CheckOrder> waitCheck(CheckListRequest request);

    List<CheckOrder> checkList(CheckListRequest request);

    boolean auditCheck(CheckOrder checkOrder);

    CheckOrder selectById(Integer checkOrderId);

    boolean updateCheckOrder(CheckOrder checkOrder);

}
