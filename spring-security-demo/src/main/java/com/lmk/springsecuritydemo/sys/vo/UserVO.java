package com.lmk.springsecuritydemo.sys.vo;

import com.lmk.springsecuritydemo.sys.entity.Role;
import com.lmk.springsecuritydemo.sys.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVO extends User {

    @ApiModelProperty(value = "角色列表")
    private List<Role> roleList;
}
