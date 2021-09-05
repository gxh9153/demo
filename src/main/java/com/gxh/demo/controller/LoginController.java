package com.gxh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/toMain")
    public String toMain(){
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String toError(){
        return "redirect:error.html";
    }

}
