package com.lmk.springsecuritydemo.sys.vo;

import com.lmk.springsecuritydemo.sys.entity.Permission;
import com.lmk.springsecuritydemo.sys.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PermissionVO extends Permission {

    @ApiModelProperty(value = "角色列表")
    private List<Role> roleList;
}
