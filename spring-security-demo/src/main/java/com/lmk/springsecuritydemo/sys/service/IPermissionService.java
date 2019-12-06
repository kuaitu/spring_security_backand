package com.lmk.springsecuritydemo.sys.service;

import com.lmk.springsecuritydemo.base.service.IBaseService;
import com.lmk.springsecuritydemo.sys.entity.Permission;
import com.lmk.springsecuritydemo.sys.vo.PermissionVO;

import java.util.List;

/**
 * <p>
  * 权限表 服务类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
public interface IPermissionService extends IBaseService<Permission> {

    List<Permission> findByAdminUserId(Integer id);

    List<PermissionVO> findAll();
}
