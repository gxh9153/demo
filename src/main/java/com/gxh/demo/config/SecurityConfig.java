package com.gxh.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Security 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Bean
    public PasswordEncoder pw(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //表单登录
        http.formLogin().
                //自定义登录页面
                loginPage("/login.html")
                //执行自定义登录逻辑
                .loginProcessingUrl("/login")//与Controller 中的/login 无关
                //登录成功后跳转的页面
               // .successForwardUrl("/toMain")//Post 请求方式跳转
                //get方式跳转页面   需要重写实线AuthorizeSuccessHandler类
                .successHandler(new MyAuthorizeSuccessHandler("/main.html"))
                //登录失败后跳转到失败页面
                .failureForwardUrl("/toError")
               // .failureHandler(new MyAuthorizeFailureHandler("/error.html"))
                ;
        //异常处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        for (String url : ignoreUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //授权
        registry
                //放行登录页面
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                //  ** 表示该目录下全部都是  * 0个或多个   ？匹配一个字符
                .antMatchers("/images/**").permitAll()
                //.regexMatchers() 正则表达式
                //.mvcMatchers().servletPath("/XXX") //匹配XXX下面所有
                //所有请求都需要被认证
                //.antMatchers("/main1.html").hasAuthority("admiN")  权限控制，单一匹配
                //.antMatchers("/main1.html").hasAnyAuthority("admin,Admin") //多个匹配，只要存在一个就行
                //根据角色匹配，不能以ROLE_开头，严格区分大小写
                //.antMatchers("/main1.html").hasRole("abC")
                //.anyRequest().authenticated()
                 .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        //关闭跨域
        http.csrf().disable();
    }
}
