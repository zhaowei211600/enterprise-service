package com.third.enterprise.dao;

import com.third.enterprise.bean.ProductProtocol;

public interface ProductProtocolMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductProtocol record);

    int insertSelective(ProductProtocol record);

    ProductProtocol selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductProtocol record);

    int updateByPrimaryKeyWithBLOBs(ProductProtocol record);

    int updateByPrimaryKey(ProductProtocol record);

    ProductProtocol selectByProductId(Integer productId);

    int updateByProductId(ProductProtocol productProtocol);
}