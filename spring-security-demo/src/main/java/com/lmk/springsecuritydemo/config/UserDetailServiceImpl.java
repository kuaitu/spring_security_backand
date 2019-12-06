package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.sys.entity.Role;
import com.lmk.springsecuritydemo.sys.service.IUserService;
import com.lmk.springsecuritydemo.sys.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO sysUser = userService.findByUserName(username);
        if (sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String pwd = passwordEncoder.encode(sysUser.getPassword());
        System.out.println(pwd);
        return new User(sysUser.getUserName(),pwd,getRoles(sysUser.getRoleList()));
    }

    private Collection<GrantedAuthority> getRoles(List<Role> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles){
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
            list.add(grantedAuthority);
        }
        return list;
    }
}
