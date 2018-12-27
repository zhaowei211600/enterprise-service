package com.third.enterprise.dao;

import com.third.enterprise.bean.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateOrderStatus(@Param("orderId") Integer orderId,
                          @Param("status") String status);
}