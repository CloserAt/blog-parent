package com.hj.blog.admin.controller;


import com.hj.blog.admin.pojo.Permission;
import com.hj.blog.admin.service.PermissionService;
import com.hj.blog.admin.vo.Result;
import com.hj.blog.admin.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParams pageParams){
        return permissionService.listPermission(pageParams);
    }

    //crud-create接口
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    //crud-update接口
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    //crud-delete接口
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

}
