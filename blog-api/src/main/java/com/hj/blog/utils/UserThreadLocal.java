package com.hj.blog.utils;

import com.hj.blog.admin.pojo.SysUser;
//单例模式
public class UserThreadLocal {
    private UserThreadLocal(){};//设置为私有，不能被new出来

    //作为线程变量隔离的
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
