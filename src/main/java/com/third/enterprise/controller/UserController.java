package com.third.enterprise.controller;

import com.third.enterprise.bean.User;
import com.third.enterprise.bean.request.LoginRequest;
import com.third.enterprise.bean.request.RegisterRequest;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.service.IOperationUserService;
import com.third.enterprise.service.IUserService;
import com.third.enterprise.service.security.OperationUserHelper;
import com.third.enterprise.service.security.OperationUser;
import com.third.enterprise.service.security.OperationToken;
import com.third.enterprise.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @Autowired
    private OperationToken operationToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OperationUserHelper jwtHelper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private IOperationUserService operationUserService;

    @GetMapping("/all")
    public List<User> listAll(){
        return userService.listAll();
    }

    @PostMapping(value = "/login")
    public UnifiedResult createAuthenticationToken(@RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtHelper.generateToken(userDetails);
        //记录登录时间
        String loginKey = "OPERATION_USER_LOGIN_TIME" + ((OperationUser)userDetails).getId();
        redisTemplate.opsForValue().set(loginKey, new Date());
        //返回登录信息
        Map<String,Object> paramMap = new HashMap<>(2);
        paramMap.put(operationToken.getHeader(),token);
        paramMap.put("userName", loginRequest.getUsername());
        return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, paramMap);
    }

    @PostMapping(value = "/register")
    public UnifiedResult userRegister(@RequestBody RegisterRequest registerRequest){
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        String key = Constants.RedisKey.OPERATION_REGISTER + registerRequest.getAccount();
        com.third.enterprise.bean.OperationUser auditingUserByNumber = operationUserService.findUserByAccount(registerRequest.getAccount());
        if(auditingUserByNumber != null){
            //unifiedResult = new UnifiedResult(false,ReturnConsts.DUPLICATE_PHONE_ERROR_CODE,ReturnConsts.DUPLICATE_PHONE_ERROR_MESSAGE,null);
            return UnifiedResultBuilder.errorResult(Constants.ACCOUNT_EXISTS_ERROR_CODE, Constants.ACCOUNT_EXISTS_ERROR_MESSAGE);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = registerRequest.getPassword();
        String encodePassword = encoder.encode(rawPassword);
        com.third.enterprise.bean.OperationUser operationUser = new com.third.enterprise.bean.OperationUser();
        operationUser.setAccount(registerRequest.getAccount());
        operationUser.setPassword(encodePassword);
        operationUser.setRealName(registerRequest.getRealName());

        //保存角色和用户信息
        return UnifiedResultBuilder.defaultSuccessResult();
    }
}
