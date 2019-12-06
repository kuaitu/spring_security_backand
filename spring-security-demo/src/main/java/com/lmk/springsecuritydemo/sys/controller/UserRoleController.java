package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.sys.entity.UserRole;
import com.lmk.springsecuritydemo.sys.service.IUserRoleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
  * 用户角色关系表 前端控制器
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Api(tags = { "用户角色关系表" })
@RestController
@RequestMapping("/sys/user-role")
public class UserRoleController extends BaseController<UserRole, UserRole, IUserRoleService> {
  public UserRoleController() {
        super(UserRole.class);
  }
}
