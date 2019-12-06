package com.lmk.springsecuritydemo.open.controller;

import com.lmk.springsecuritydemo.open.service.IOpenService;
import com.lmk.springsecuritydemo.utils.ImageCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lmk.springsecuritydemo.utils.ImageCodeConstants.SESSION_KEY;

@Api(tags = { "开放接口" })
@RestController
@RequestMapping("/Open")
public class OpenController {

    @Autowired
    private IOpenService service;

    @Autowired
    private SessionStrategy sessionStrategy;

    @GetMapping("verificationCode.action")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = service.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

}
