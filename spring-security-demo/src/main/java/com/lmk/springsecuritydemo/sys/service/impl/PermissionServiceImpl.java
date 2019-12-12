package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.PermissionMapper;
import com.lmk.springsecuritydemo.sys.entity.Permission;
import com.lmk.springsecuritydemo.sys.service.IPermissionService;
import com.lmk.springsecuritydemo.sys.vo.Menus;
import com.lmk.springsecuritydemo.sys.vo.PermissionVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
  * 权限表 服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {
    @Override
    public Wrapper<Permission> buildWrapper(Permission example) {
        QueryWrapper<Permission> q = new QueryWrapper<>();
        return q;
    }

    @Override
    public List<Permission> findByAdminUserId(Integer id) {
        return baseMapper.findByAdminUserId(id);
    }

    @Override
    public List<PermissionVO> findAll() {
        return baseMapper.findAll();
    }

    /**
     * 根据用户id查询菜单树，用户id为空则，则查询系统整棵菜单树
     * @param userId
     * @return
     */
    @Override
    public List<Menus> findMenusByUserId(Integer userId) {
        return baseMapper.findMenusByUserId(userId);
    }

    /**
     * 根据角色id查询菜单树
     * @param roleId
     * @return
     */
    @Override
    public List<Menus> findMenusByRoleId(Integer roleId) {
        return baseMapper.findMenusByRoleId(roleId);
    }


}
