package com.hj.blog.admin.service;

import com.hj.blog.admin.pojo.Admin;
import com.hj.blog.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest httpServletRequest, Authentication authentication) {
        //权限认证
        //请求路径
        String requestURI = httpServletRequest.getRequestURI();
        Object principal = authentication.getPrincipal();//userDetail信息=principal
        if (principal == null || "anonymousUser".equals(principal)){
            //如果为空或者是匿名用户就是未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null){
            return  false;
        }
        if (1 == admin.getId()){
            //超级管理员直接放行
            return true;
        }
        Long id = admin.getId();
        List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
        requestURI = StringUtils.split(requestURI,'?')[0];//url里面可能有？传参，此处分割一下，取第0位，拿到不带参数的部分
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return true;
    }
}
