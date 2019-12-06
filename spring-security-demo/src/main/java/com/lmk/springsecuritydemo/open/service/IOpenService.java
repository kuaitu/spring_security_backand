package com.lmk.springsecuritydemo.open.service;

import com.lmk.springsecuritydemo.utils.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface IOpenService {
    ImageCode generate(ServletWebRequest servletWebRequest);
}
