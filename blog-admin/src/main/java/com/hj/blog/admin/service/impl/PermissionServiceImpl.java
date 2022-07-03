package com.hj.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hj.blog.admin.mapper.PermissionMapper;
import com.hj.blog.admin.pojo.Permission;
import com.hj.blog.admin.service.PermissionService;
import com.hj.blog.admin.vo.PageResult;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.PageParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result listPermission(PageParams pageParams) {
        /*
        要的数据是管理后台的 表的所有字段 Permission
        分页查询
         */
        Page<Permission> page = new Page<>(pageParams.getCurrentPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParams.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParams.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
