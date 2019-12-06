package com.lmk.springsecuritydemo.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lmk.springsecuritydemo.base.entity.BaseIdModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
  * 权限角色关系表
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_permission_role")
@ApiModel(value="PermissionRole对象", description="权限角色关系表")
public class PermissionRole extends BaseIdModel
<Integer> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private Integer permissionId;


}
