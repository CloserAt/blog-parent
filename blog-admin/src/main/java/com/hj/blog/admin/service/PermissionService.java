package com.hj.blog.admin.service;

import com.hj.blog.admin.pojo.Permission;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.PageParams;

public interface PermissionService {
    Result listPermission(PageParams pageParams);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
