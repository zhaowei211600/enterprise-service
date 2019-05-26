package com.third.enterprise.dao;

import com.third.enterprise.bean.CheckOrder;
import com.third.enterprise.bean.request.CheckListRequest;

import java.util.List;

public interface CheckOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckOrder record);

    int insertSelective(CheckOrder record);

    CheckOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckOrder record);

    int updateByPrimaryKey(CheckOrder record);

    List<CheckOrder> checkList(CheckListRequest request);

    List<CheckOrder> settleList(CheckListRequest request);

    int hasOpenCheckOrder(Integer orderId);

    List<CheckOrder> listAll(CheckListRequest request);
}