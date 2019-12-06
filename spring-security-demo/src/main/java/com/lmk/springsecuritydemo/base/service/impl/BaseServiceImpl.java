package com.lmk.springsecuritydemo.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmk.springsecuritydemo.base.entity.BaseModel;
import com.lmk.springsecuritydemo.base.service.IBaseService;

public abstract class BaseServiceImpl<Mapper extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<Mapper, T> implements IBaseService<T> {
}
