package com.hj.blog.admin.service;

import com.hj.blog.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    //springSecurity提供的loadUserByUsername方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登陆的时候会把username传到这里
        //我们通过username查询admin表，若存在该username,则把密码告诉springSecurity
        Admin admin = this.adminService.findAdminByUsername(username);
        if (admin == null) {
            //登陆失败
            return null;
        }
        //此处密码验证交给spring Security
        UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());
        return null;
    }
}
