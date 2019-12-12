package com.lmk.springsecuritydemo.sys.vo;

import com.lmk.springsecuritydemo.sys.entity.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@ApiModel(value="Menus对象", description="首页左侧菜单树")
public class Menus extends Permission {

    // findMenusByUserId（根据用户id查询菜单树）用到
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    // findMenusByRoleId（根据角色id查询菜单树）用到
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    // 给角色设置权限时用到
    @ApiModelProperty(value = "是否选中")
    private Boolean isChoose;

    @ApiModelProperty(value = "子菜单或权限")
    private List<Menus> children;
}
