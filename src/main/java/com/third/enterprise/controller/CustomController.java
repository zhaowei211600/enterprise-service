package com.third.enterprise.controller;

import com.third.enterprise.bean.User;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IProductService;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/operation/custom")
public class CustomController {

    private static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IProductService productService;

    @RequestMapping("/check")
    public UnifiedResult waitCheck(Integer productId){

        List<User> waitCheckUser = userService.findByOrderId(productId);
        if(waitCheckUser != null && waitCheckUser.size() >0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, waitCheckUser);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/choose")
    public UnifiedResult chooseUser(Integer productId, Integer userId , Integer orderId){

        if(productService.chooseUser(productId, userId, orderId)){
            return UnifiedResultBuilder.defaultSuccessResult();
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }
}
