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
  * 用户表
  * </p>
 *
 * @author linmk
 * @since 2019-12-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户表")
public class User extends BaseIdModel
<Integer> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String userNameZh;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "是否启动")
    private String enabled;

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String userface;

    @ApiModelProperty(value = "备注")
    private String remark;


}
