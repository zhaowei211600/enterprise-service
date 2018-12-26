package com.third.enterprise.service;

import com.third.enterprise.bean.User;

import java.util.List;

public interface IUserService {

    List<User> listAll();

    List<User> findByOrderId(Integer orderId);

}
