package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.filter.ValidateImageCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private LoginSuccessAuthenticationHandler successAuthenticationHandler;

    @Autowired
    private LoginFailureAuthenticationHandler failureAuthenticationHandler;

    @Autowired
    private AuthenticationAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UrlAccessDecisionManager decisionManager;

    @Autowired
    private UrlPathFilterInvocationSecurityMetadataSource urlPathFilterInvocationSecurityMetadataSource;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private ValidateImageCodeFilter validateImageCodeFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/home.html","/favicon.ico");

        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/lib/**");
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/lang/**");
        web.ignoring().antMatchers("/login/**");
        web.ignoring().antMatchers("/error/**");
        web.ignoring().antMatchers("/login.html");

        // 系统所有不需要登录就能访问的接口
        web.ignoring().antMatchers("/Open/**");

        // 系统所有不需要登录就能访问的接口
        web.ignoring().antMatchers("/ws/**");

        //解决服务注册url被拦截的问题
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/v2/**");
        web.ignoring().antMatchers("/**/*.json");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.addFilterBefore(filter,CsrfFilter.class)
            .addFilterBefore(validateImageCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf().disable()
            .authorizeRequests()
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    o.setAccessDecisionManager(decisionManager);
                    o.setSecurityMetadataSource(urlPathFilterInvocationSecurityMetadataSource);
                    return o;
                }
            })

            .anyRequest()
            .authenticated()// 其他 url 需要身份认证

            .and()
            .formLogin()  //开启登录
            //.loginPage("/login.html")
            .loginProcessingUrl("/User/login.action")
            .successHandler(successAuthenticationHandler)
            .failureHandler(failureAuthenticationHandler)
            .permitAll()

            .and()
            .logout()
            .logoutUrl("/logout")
            .permitAll()
            .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
