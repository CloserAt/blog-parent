package com.hj.blog.admin.controller;

import com.hj.blog.Service.LoginService;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.RegisterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody RegisterParams registerParams) {
        //sso 单点登陆，后期如果把登陆注册功能提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(registerParams);
    }
}
