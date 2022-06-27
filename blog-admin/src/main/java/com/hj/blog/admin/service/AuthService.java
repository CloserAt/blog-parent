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

    public boolean auth(HttpServletRequest request, Authentication authentication) {
        //权限认证
        String requestURI = request.getRequestURI();//请求路径
        Object principal = authentication.getPrincipal();//当前用户的信息==userDetail
        //true代表放行 false 代表拦截
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        Admin adminByUsername = adminService.findAdminByUsername(userDetails.getUsername());
        if (adminByUsername == null) {
            return false;
        }
        //超级管理员，直接放行
        if (adminByUsername.getId() == 1) {
            return true;
        }
        Long id = adminByUsername.getId();
        List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
        requestURI = StringUtils.split(requestURI,'?')[0];//url连接里面可能会带有?的传参，此处split,取第0位
        for (Permission permission : permissionList) {
            if (requestURI.equals(permission.getPath())) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
