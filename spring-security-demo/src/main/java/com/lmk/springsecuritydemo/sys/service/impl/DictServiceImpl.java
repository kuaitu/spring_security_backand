package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.DictMapper;
import com.lmk.springsecuritydemo.sys.entity.Dict;
import com.lmk.springsecuritydemo.sys.service.IDictService;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 字典类别表 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements IDictService {
    @Override
    public Wrapper<Dict> buildWrapper(Dict example) {
        QueryWrapper<Dict> q = new QueryWrapper<>();
        return q;
    }
}
