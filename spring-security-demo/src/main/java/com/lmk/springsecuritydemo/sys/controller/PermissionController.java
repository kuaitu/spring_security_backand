package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.base.utils.Ret;
import com.lmk.springsecuritydemo.sys.entity.Permission;
import com.lmk.springsecuritydemo.sys.service.IPermissionService;
import com.lmk.springsecuritydemo.sys.service.IUserService;
import com.lmk.springsecuritydemo.sys.vo.Menus;
import com.lmk.springsecuritydemo.sys.vo.UserVO;
import com.lmk.springsecuritydemo.utils.SysUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
  * 权限表 前端控制器
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Api(tags = { "权限表" })
@RestController
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController<Permission, Permission, IPermissionService> {
    public PermissionController() {
        super(Permission.class);
    }

    @Autowired
    private IUserService userService;

    @PostMapping(value = "findByUserId.action")
    public @ResponseBody
    Ret<List<Permission>> findByUserId(@RequestParam Integer userId) {
        List<Permission> permissionList = service.findByAdminUserId(userId);
        return Ret.ok(permissionList);
    }

    @ApiModelProperty(value = "首页左侧菜单树")
    @PostMapping(value = "findMenusByUserId.action")
    public @ResponseBody
    Ret<List<Menus>> findMenusByUserId() {
        User user = (User) SysUserUtil.getUser();
        UserVO sysUser = userService.findByUserName(user.getUsername());
        List<Menus> menus = service.findMenusByUserId(sysUser.getId());
        return Ret.ok(menus);
    }
}
