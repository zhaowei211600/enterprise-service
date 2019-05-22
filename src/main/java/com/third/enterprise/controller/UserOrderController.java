package com.third.enterprise.controller;

import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IOrderService;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IOrderService orderService;

    @PostMapping("/confirm")
    public UnifiedResult waitConfirmUser(Integer orderId){
        List<User> confirmUserList = userService.findByOrderId(orderId);
        if(confirmUserList != null && confirmUserList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, confirmUserList);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

}
