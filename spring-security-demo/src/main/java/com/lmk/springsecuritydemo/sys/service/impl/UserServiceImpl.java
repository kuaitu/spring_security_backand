package com.lmk.springsecuritydemo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl;
import com.lmk.springsecuritydemo.sys.dao.UserMapper;
import com.lmk.springsecuritydemo.sys.entity.User;
import com.lmk.springsecuritydemo.sys.service.IUserService;
import com.lmk.springsecuritydemo.sys.vo.UserVO;
import org.springframework.stereotype.Service;

/**
 * <p>
  *  服务实现类
  * </p>
 *
 * @author linmk
 * @since 2019-11-05
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public Wrapper<User> buildWrapper(User example) {
        QueryWrapper<User> q = new QueryWrapper<>();
        return q;
    }

    @Override
    public UserVO findByUserName(String username) {
        return baseMapper.findByUserName(username);
    }
}
