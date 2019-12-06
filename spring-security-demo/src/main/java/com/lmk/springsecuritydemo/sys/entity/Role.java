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
  * 角色表
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value="Role对象", description="角色表")
public class Role extends BaseIdModel
<Integer> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色代码")
    private String roleName;

    @ApiModelProperty(value = "角色名称")
    private String roleNameZh;


}
