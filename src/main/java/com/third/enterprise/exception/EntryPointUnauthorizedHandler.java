package com.third.enterprise.exception;

import com.alibaba.fastjson.JSON;
import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.util.Constants;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Jingjiadong
 * @Description:处理401错误
 * @Date: 11:08 AM 08/11/2017
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        UnifiedResult unifiedResult = new UnifiedResult(false, Constants.TOKEN_ERROR_CODE, Constants.TOKEN_ERROR_MESSAGE,null);
        httpServletResponse.getWriter().println(JSON.toJSONString(unifiedResult));
        httpServletResponse.getWriter().flush();
    }
}
