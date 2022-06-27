package com.hj.blog.Service;

import com.hj.blog.admin.pojo.SysUser;

public interface TokenService {
    SysUser checkToken(String token);

}
