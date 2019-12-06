package com.lmk.springsecuritydemo.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmk.springsecuritydemo.sys.entity.User;
import com.lmk.springsecuritydemo.sys.vo.UserVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
public interface UserMapper extends BaseMapper<User> {
    UserVO findByUserName(String username);
}
