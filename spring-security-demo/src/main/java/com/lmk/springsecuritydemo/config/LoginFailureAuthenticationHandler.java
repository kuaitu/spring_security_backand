package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.base.utils.Ret;
import com.lmk.springsecuritydemo.utils.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qiumin
 * @create 2019/1/13 13:06
 * @desc
 **/
@Component
public class LoginFailureAuthenticationHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print(JsonUtil.beanToStr(Ret.err(e.getMessage())));
        writer.flush();
        writer.close();
    }
}
