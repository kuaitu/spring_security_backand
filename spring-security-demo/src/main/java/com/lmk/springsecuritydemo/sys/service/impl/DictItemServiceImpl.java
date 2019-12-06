package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.DictItemMapper;
import com.lmk.springsecuritydemo.sys.entity.DictItem;
import com.lmk.springsecuritydemo.sys.service.IDictItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 字典项 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-21
 */
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemMapper, DictItem> implements IDictItemService {
    @Override
    public Wrapper<DictItem> buildWrapper(DictItem example) {
        QueryWrapper<DictItem> q = new QueryWrapper<>();
        return q;
    }
}
