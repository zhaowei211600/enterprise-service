package com.third.enterprise.service.impl;

import com.third.enterprise.bean.ProductProtocol;
import com.third.enterprise.dao.ProductProtocolMapper;
import com.third.enterprise.service.IProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocolServiceImpl implements IProtocolService{

    private static final Logger logger = LoggerFactory.getLogger(ProtocolServiceImpl.class);

    @Autowired
    private ProductProtocolMapper protocolMapper;

    @Override
    public ProductProtocol selectByProductId(Integer productId) {
        return protocolMapper.selectByProductId(productId);
    }

    @Override
    public boolean saveProtocol(ProductProtocol productProtocol) {

        if(productProtocol.getId() != null && productProtocol.getId() != 0){
            if(protocolMapper.updateByPrimaryKey(productProtocol) > 0){
                return true;
            }
        }else{
            if(protocolMapper.insertSelective(productProtocol) > 0){
                return true;
            }
        }
        return false;
    }
}
