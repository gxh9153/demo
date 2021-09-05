package com.gxh.demo.config;


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
                //自定义登录逻辑
                .loginProcessingUrl("/login")//与Controller 中的/login 无关
                //登录成功后跳转的页面
                .successForwardUrl("/toMain")//Post 请求方式跳转
                //登录失败后跳转到失败页面
                .failureForwardUrl("/toError");

        for (String url : ignoreUrlsConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //授权
        registry
                //放行登录页面
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                //所有请求都需要被认证
                .anyRequest().authenticated();

        //关闭跨域
        http.csrf().disable();
    }
}
