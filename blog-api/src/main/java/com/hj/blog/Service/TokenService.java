package com.hj.blog.Service;

import com.hj.blog.dao.pojo.SysUser;

public interface TokenService {
    SysUser checkToken(String token);

}
