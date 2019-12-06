package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.sys.entity.PermissionRole;
import com.lmk.springsecuritydemo.sys.service.IPermissionRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
  * 权限角色关系表 前端控制器
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Api(tags = { "权限角色关系表" })
@RestController
@RequestMapping("/sys/permission-role")
public class PermissionRoleController extends BaseController<PermissionRole, PermissionRole, IPermissionRoleService> {
    public PermissionRoleController() {
        super(PermissionRole.class);
    }
}
