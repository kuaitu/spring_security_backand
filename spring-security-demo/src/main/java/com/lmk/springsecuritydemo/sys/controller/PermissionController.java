package com.lmk.springsecuritydemo.sys.controller;


import com.lmk.springsecuritydemo.base.controller.BaseController;
import com.lmk.springsecuritydemo.base.utils.Ret;
import com.lmk.springsecuritydemo.sys.entity.Permission;
import com.lmk.springsecuritydemo.sys.service.IPermissionService;
import io.swagger.annotations.Api;
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

    @PostMapping(value = "findByUserId.action")
    public @ResponseBody
    Ret<List<Permission>> findByUserId(@RequestParam Integer userId) {
        List<Permission> jurisdictioncList = service.findByAdminUserId(userId);
        return Ret.ok(jurisdictioncList);
    }
}
