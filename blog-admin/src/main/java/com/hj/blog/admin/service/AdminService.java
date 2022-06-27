package com.hj.blog.admin.service;

import com.hj.blog.admin.pojo.Admin;
import com.hj.blog.admin.pojo.Permission;

import java.util.List;

public interface AdminService {
    Admin findAdminByUsername(String username);

    List<Permission> findPermissionByAdminId(Long id);
}
