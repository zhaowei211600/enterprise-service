package com.third.enterprise.service.impl;

import com.third.enterprise.bean.User;
import com.third.enterprise.dao.UserMapper;
import com.third.enterprise.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService{

    private static final Logger Logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> listAll() {
        return userMapper.listAll();
    }

    @Override
    public List<User> findByOrderId(Integer orderId) {
        return userMapper.findByOrderId(orderId);
    }
}
