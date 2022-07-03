package com.hj.blog.service;


import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.LoginParams;
import com.hj.blog.admin.vo.params.RegisterParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    Result logOut(String token);

    Result register(RegisterParams registerParams);
}
