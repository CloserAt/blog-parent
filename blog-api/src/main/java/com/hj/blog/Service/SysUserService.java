package com.hj.blog.Service;

import com.hj.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findAuthorsByAuthorId(Long authorId);
}
