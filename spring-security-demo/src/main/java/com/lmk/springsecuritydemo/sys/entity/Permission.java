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
  * 权限表
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="权限表")
public class Permission extends BaseIdModel
<Integer> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限路径")
    private String url;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "模块页面路径")
    private String component;

    @ApiModelProperty(value = "名称")
    private String permissionName;

    @ApiModelProperty(value = "图标")
    private String iconCls;

    @ApiModelProperty(value = "页面缓存")
    private Boolean keepAlive;

    @ApiModelProperty(value = "访问权限")
    private Boolean requireAuth;

    @ApiModelProperty(value = "父级菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "排序")
    private Integer sortNumber;
}
