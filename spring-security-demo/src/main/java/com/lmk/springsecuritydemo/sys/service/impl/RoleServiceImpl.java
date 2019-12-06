package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.RoleMapper;
import com.lmk.springsecuritydemo.sys.entity.Role;
import com.lmk.springsecuritydemo.sys.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 角色表 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public Wrapper<Role> buildWrapper(Role example) {
        QueryWrapper<Role> q = new QueryWrapper<>();
        return q;
    }
}
