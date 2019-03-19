package com.third.trading.controller;

import com.third.trading.bean.User;
import com.third.trading.bean.request.CustomListRequest;
import com.third.trading.bean.request.CustomRegisterRequest;
import com.third.trading.bean.response.UnifiedResult;
import com.third.trading.bean.response.UnifiedResultBuilder;
import com.third.trading.service.IProductService;
import com.third.trading.service.IUserService;
import com.third.trading.util.Constants;
import com.third.trading.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/operation/custom")
public class CustomController {

    private static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Resource
    private IUserService userService;

    @Resource
    private IProductService productService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/check")
    public UnifiedResult waitCheck(Integer productId){

        List<User> waitCheckUser = userService.findByOrderId(productId);
        if(waitCheckUser != null && waitCheckUser.size() >0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, waitCheckUser);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/list")
    public UnifiedResult listCustiom(CustomListRequest request){

        List<User> customList = userService.listAll(request);
        if(customList != null && customList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, customList, Page.toPage(customList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping(value = "/register")
    public UnifiedResult customRegister(@RequestBody CustomRegisterRequest request){
        String redisKey = Constants.RedisKey.CUSTOM_REGISTER + request.getPhone();
        if(!request.getMessageCode().equals(stringRedisTemplate.opsForValue().get(redisKey))){
            return UnifiedResultBuilder.errorResult(Constants.MESSAGE_CODE_ERROR_CODE,
                    Constants.MESSAGE_CODE_ERROR_MESSAGE);
        }
        if(StringUtils.isEmpty(request.getPassword())
                || !request.getPassword().equals(request.getPasswordAgain())){
            return UnifiedResultBuilder.errorResult(Constants.INCONSISTENT_PASSWORD_ERROR_CODE,
                    Constants.INCONSISTENT_PASSWORD_ERROR_MESSAGE);
        }
        //TODO
        return UnifiedResultBuilder.errorResult(Constants.REGISTER_ERROR_CODE,
                Constants.REGISTER_ERROR_MESSAGE);
    }

    @RequestMapping("/confirm")
    public UnifiedResult confirmUser(Integer userId){

        User customer = userService.findByUserId(userId);
        if(customer != null){
            if(Constants.UserState.WAIT_CONFIRM.equals(customer.getStatus())){
                customer.setStatus(Constants.UserState.PASSED);
                customer.setPassTime(new Date());
                if(userService.updateUser(customer)){
                    return UnifiedResultBuilder.defaultSuccessResult();
                }
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/stop")
    public UnifiedResult stopUser(Integer userId){

        User customer = userService.findByUserId(userId);
        if(customer != null){
            if(userService.updateStatus(userId, Constants.UserState.STOPED)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/reset")
    public UnifiedResult resetUserPassword(Integer userId, String password){

        User customer = userService.findByUserId(userId);
        if(customer != null && !StringUtils.isEmpty(password)){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String rawPassword = encoder.encode(password);
            customer.setPassword(rawPassword);
            if(userService.updateUser(customer)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.DATA_HANDLE_ERROR_CODE,
                Constants.DATA_HANDLE_ERROR_MESSAGE);
    }
}
