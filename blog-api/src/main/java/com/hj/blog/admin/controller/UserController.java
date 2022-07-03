package com.hj.blog.admin.controller;

import com.hj.blog.service.SysUserService;
import com.hj.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    /*
    接口url：/users/currentUser

    请求方式：GET

    请求参数：

    | 参数名称      | 参数类型 | 说明            |
    | ------------- | -------- | --------------- |
    | Authorization | string   | 头部信息(TOKEN) |
    |               |          |                 |
    |               |          |                 |

    返回数据：

    ~~~json
    {
        "success": true,
        "code": 200,
        "msg": "success",
        "data": {
            "id":1,
            "account":"1",
            "nickaname":"1",
            "avatar":"ss"
        }
    }
    ~~~
     */
    //登陆后获取用户信息-接口
    //token前端获取到之后，会存储到申通storage中h5，本地存储

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
