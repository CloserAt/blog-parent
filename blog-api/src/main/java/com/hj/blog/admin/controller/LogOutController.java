package com.hj.blog.admin.controller;

import com.hj.blog.service.LoginService;
import com.hj.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("logout")
public class LogOutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logOut(@RequestHeader("Authorization") String token) {
        return loginService.logOut(token);
    }
}
