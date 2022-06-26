package com.hj.blog.Service;


import com.hj.blog.dao.pojo.SysUser;
import com.hj.blog.vo.Result;
import com.hj.blog.vo.params.LoginParams;
import com.hj.blog.vo.params.RegisterParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    Result logOut(String token);

    Result register(RegisterParams registerParams);
}
