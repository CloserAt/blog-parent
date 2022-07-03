package com.hj.blog.service;

import com.hj.blog.admin.pojo.SysUser;

public interface TokenService {
    SysUser checkToken(String token);

}
