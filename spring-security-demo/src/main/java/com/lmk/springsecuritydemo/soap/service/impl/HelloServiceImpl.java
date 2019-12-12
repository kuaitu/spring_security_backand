package com.lmk.springsecuritydemo.soap.service.impl;

import com.lmk.springsecuritydemo.soap.service.IHelloService;
import com.lmk.springsecuritydemo.sys.entity.User;
import com.lmk.springsecuritydemo.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.interceptor.InInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@InInterceptors(interceptors={"com.lmk.springsecuritydemo.filter.AuthInterceptor"})
@WebService(serviceName = "IHelloService", targetNamespace = "http://springsecuritydemo.lmk.com/", endpointInterface = "com.lmk.springsecuritydemo.soap.service.IHelloService")
@Component
@Slf4j
public class HelloServiceImpl implements IHelloService {


    @Autowired
    private IUserService service;

    @Override
    public User getUserById(int id) {
        return service.getById(id);
    }

    @Override
    public int addUser(User user) {
        service.save(user);
        log.info("save user [" + user.getId() + "]");
        return user.getId();
    }
}
