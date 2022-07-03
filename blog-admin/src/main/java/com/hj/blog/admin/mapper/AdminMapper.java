package com.hj.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hj.blog.admin.pojo.Admin;
import com.hj.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    //@Select("SELECT * FROM hj_permission where id in (select permission_id from hj_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
