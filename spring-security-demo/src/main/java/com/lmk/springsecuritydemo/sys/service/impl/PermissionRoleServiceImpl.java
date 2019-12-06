package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.PermissionRoleMapper;
import com.lmk.springsecuritydemo.sys.entity.PermissionRole;
import com.lmk.springsecuritydemo.sys.service.IPermissionRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 权限角色关系表 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Service
public class PermissionRoleServiceImpl extends BaseServiceImpl<PermissionRoleMapper, PermissionRole> implements IPermissionRoleService {
    @Override
    public Wrapper<PermissionRole> buildWrapper(PermissionRole example) {
        QueryWrapper<PermissionRole> q = new QueryWrapper<>();
        return q;
    }
}
