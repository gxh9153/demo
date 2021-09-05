package com.gxh.demo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface MyService {

    //判断是否有权限访问
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
