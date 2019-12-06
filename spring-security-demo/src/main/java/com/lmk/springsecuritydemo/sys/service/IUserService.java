package com.lmk.springsecuritydemo.sys.service;

import com.lmk.springsecuritydemo.base.service.IBaseService;
import com.lmk.springsecuritydemo.sys.entity.User;
import com.lmk.springsecuritydemo.sys.vo.UserVO;

/**
 * <p>
  *  服务类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
public interface IUserService extends IBaseService<User> {

    UserVO findByUserName(String username);
}
