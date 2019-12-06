package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.base.utils.Ret;
import com.lmk.springsecuritydemo.sys.service.IUserService;
import com.lmk.springsecuritydemo.sys.vo.UserVO;
import com.lmk.springsecuritydemo.utils.JsonUtil;
import com.lmk.springsecuritydemo.utils.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author qiumin
 * @create 2019/1/13 12:59
 * @desc
 **/
@Component
public class LoginSuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        User user = (User) SysUserUtil.getUser();
        UserVO sysUser = userService.findByUserName(user.getUsername());

        writer.print(JsonUtil.beanToStr(Ret.ok(sysUser)));
        writer.flush();
        writer.close();
    }
}
