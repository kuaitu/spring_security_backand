package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.UserRoleMapper;
import com.lmk.springsecuritydemo.sys.entity.UserRole;
import com.lmk.springsecuritydemo.sys.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
  * 用户角色关系表 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Override
    public Wrapper<UserRole> buildWrapper(UserRole example) {
        QueryWrapper<UserRole> q = new QueryWrapper<>();
        return q;
    }
}
