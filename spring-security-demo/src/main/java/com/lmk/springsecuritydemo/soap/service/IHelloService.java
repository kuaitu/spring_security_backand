package com.lmk.springsecuritydemo.soap.service;

import com.lmk.springsecuritydemo.sys.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace="http://springsecuritydemo.lmk.com/")
public interface IHelloService {

    @WebMethod
    User getUserById(@WebParam(name = "id") int id);

    @WebMethod
    int addUser(@WebParam(name = "user") User user);
}
