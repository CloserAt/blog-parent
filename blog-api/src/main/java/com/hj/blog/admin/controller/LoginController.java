package com.hj.blog.admin.controller;

import com.hj.blog.Service.LoginService;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    /*
    接口url：/login

    请求方式：POST

    请求参数：

    | 参数名称 | 参数类型 | 说明 |
    | -------- | -------- | ---- |
    | account  | string   | 账号 |
    | password | string   | 密码 |
    |          |          |      |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data": "token"
    }
    ~~~
     */

    //此处不适用SysUserService是因为要高内聚，低耦合，SysUserService是负责user表相关的操作
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result accountLogin(@RequestBody LoginParams loginParams) {
        //登陆需要验证用户，访问用户表
        return loginService.login(loginParams);
    }
}
