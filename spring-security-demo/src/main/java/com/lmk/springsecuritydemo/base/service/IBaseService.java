package com.lmk.springsecuritydemo.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lmk.springsecuritydemo.base.entity.BaseModel;

public interface IBaseService<T extends BaseModel> extends IService<T> {
    Wrapper<T> buildWrapper(T item);
}
