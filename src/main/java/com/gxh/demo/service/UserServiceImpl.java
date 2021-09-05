package com.gxh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 登录实现类
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder pw;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       // System.out.println("有没有执行自定义登录逻辑");

        //1根据username查询数据库
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        //根据查询的对象比较密码
        String password = pw.encode("123456");

        //返回用户对象
        return new User("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_efg"));
    }
}
