package com.hj.blog.service;

import com.hj.blog.admin.pojo.SysUser;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.UserVo;

public interface SysUserService {
    SysUser findAuthorsByAuthorId(Long authorId);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser newSysUser);

    UserVo findUserVoById(Long authorId);
}
