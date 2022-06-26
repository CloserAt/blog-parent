package com.hj.blog.Service;

import com.hj.blog.dao.pojo.SysUser;
import com.hj.blog.vo.Result;

public interface SysUserService {
    SysUser findAuthorsByAuthorId(Long authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser newSysUser);

}
