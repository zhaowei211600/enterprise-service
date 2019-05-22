package com.third.enterprise.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.enterprise.bean.CheckOrder;
import com.third.enterprise.bean.request.CheckListRequest;
import com.third.enterprise.dao.CheckOrderMapper;
import com.third.enterprise.service.ICheckOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOrderServiceImpl implements ICheckOrderService{

    private static final Logger logger = LoggerFactory.getLogger(CheckOrderServiceImpl.class);

    @Autowired
    private CheckOrderMapper checkOrderMapper;

    @Override
    public List<CheckOrder> checkHistory(CheckListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        request.setStatus(null);
        return checkOrderMapper.checkList(request);
    }

    @Override
    public List<CheckOrder> waitCheck(CheckListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        request.setStatus("1");
        return checkOrderMapper.checkList(request);
    }

    @Override
    public List<CheckOrder> settleList(CheckListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return checkOrderMapper.settleList(request);
    }

    @Override
    public boolean auditCheck(CheckOrder checkOrder) {
        if(checkOrderMapper.updateByPrimaryKeySelective(checkOrder) > 0){
            return true;
        }
        return false;
    }

    @Override
    public CheckOrder selectById(Integer checkOrderId) {
        return checkOrderMapper.selectByPrimaryKey(checkOrderId);
    }

    @Override
    public boolean updateCheckOrder(CheckOrder checkOrder) {
        if(checkOrderMapper.updateByPrimaryKeySelective(checkOrder) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveCheckOrder(CheckOrder checkOrder) {

        if(checkOrderMapper.insertSelective(checkOrder) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasOpenCheckOrder(Integer orderId) {
        if(checkOrderMapper.hasOpenCheckOrder(orderId) > 0){
            return true;
        }
        return false;
    }
}
