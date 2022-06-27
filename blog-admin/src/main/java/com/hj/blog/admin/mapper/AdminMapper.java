package com.hj.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hj.blog.admin.pojo.Admin;
import com.hj.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    List<Permission> findPermissionByAdminId(Long id);
}
