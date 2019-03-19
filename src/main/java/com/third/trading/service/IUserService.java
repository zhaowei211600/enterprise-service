package com.third.trading.service;

import com.third.trading.bean.User;
import com.third.trading.bean.request.CustomListRequest;

import java.util.List;

public interface IUserService {

    List<User> listAll(CustomListRequest request);

    List<User> findByOrderId(Integer orderId);

    List<User> findByProductId(Integer productId);

    User findByUserId(Integer userId);

    boolean updateStatus(Integer userId, String status);

    boolean updateUser(User user);
}
