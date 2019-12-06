package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.sys.entity.Role;
import com.lmk.springsecuritydemo.sys.service.IPermissionService;
import com.lmk.springsecuritydemo.sys.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author qiumin
 * @create 2019/1/13 13:13
 * @desc
 **/
@Component
public class UrlPathFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private IPermissionService permissionService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        List<PermissionVO> all = permissionService.findAll();
        for (PermissionVO menu : all) {
            if (menu.getRoleList().size() != 0 && antPathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roles = menu.getRoleList();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getRoleName();
                }
                return SecurityConfig.createList(values);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
