package com.third.enterprise.dao;

import com.third.enterprise.bean.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> listAll();

    List<User> findByOrderId(Integer orderId);

    List<User> findByProductId(Integer productId);
}